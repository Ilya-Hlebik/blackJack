package com.blackJack.service;


import com.blackJack.dbo.CardEntity;
import com.blackJack.dbo.GameEntity;
import com.blackJack.dbo.GameStep;
import com.blackJack.dbo.User;
import com.blackJack.enumeration.GameStatus;
import com.blackJack.repository.GameRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class GameService
{
    private final CardService cardService;

    private final GameRepository gameRepository;

    private final StepService stepService;

    private final UserService userService;


    public String createGame(final Principal req)
    {
        final List<String> deck = cardService.getDeck()
                .stream()
                .map(CardEntity::getName)
                .filter(s -> !"CB".equals(s))
                .collect(Collectors.toList());
        Collections.shuffle(deck);
        final User user = userService.findMe(req);
        return gameRepository.save(
                new GameEntity(new LinkedHashSet<>(deck), Collections.emptySet(), Collections.emptySet(), 0, 0, 0, 0,
                        false,
                        GameStatus.IN_PROGRESS, false,user, Collections.emptyList()))
                .getId();
    }


    public GameEntity getGameById(final String gameId)
    {
        return gameRepository.findById(gameId)
                .orElseThrow();
    }


    public void save(final GameEntity game)
    {
        gameRepository.save(game);
    }


    public GameEntity getGameWithResults(final String gameId)
    {
        getDealerCards(gameId);
        getPlayerCards(gameId);
        final GameEntity gameEntity = getGameById(gameId);
        final GameStep step = new GameStep(gameEntity.getId(), 0, gameEntity.getDealerCards(),
                gameEntity.getPlayerCards(), gameEntity.getPlayerSum(), gameEntity.getPlayerAltSum(),
                gameEntity.getDealerSum(), gameEntity.getDealerAltSum());
        stepService.save(step);
        gameEntity.setGameSteps(Collections.singletonList(step));
        if (GameStatus.IN_PROGRESS.equals(gameEntity.getGameStatus()) && gameEntity.getPlayerCards()
                .size() == 2 && gameEntity.getPlayerSum() == 21)
        {
            gameEntity.setGameStatus(GameStatus.PLAYER_BJ);
        }
        save(gameEntity);
        return gameEntity;
    }


    @Transactional
    public void getDealerCards(final String gameId)
    {
        final GameEntity game = getGameById(gameId);
        if (!game.isGameLoaded())
        {
            final String dealerCard = game.getDeck()
                    .iterator()
                    .next();
            game.getDeck()
                    .remove(dealerCard);
            final CardEntity cardEntity = cardService.findByName(dealerCard);
            game.setDealerCards(Collections.singleton(cardEntity));
            final int value = Integer.parseInt(cardEntity.getValue());
            game.setDealerSum(value);
            game.setDealerAltSum(value == 11 ? 1 : value);
            save(game);
        }
    }


    @Transactional
    public void getPlayerCards(final String gameId)
    {
        final GameEntity game = getGameById(gameId);
        if (!game.isGameLoaded())
        {
            final List<String> playerCards = game.getDeck()
                    .stream()
                    .limit(2)
                    .collect(Collectors.toList());
            game.getDeck()
                    .removeAll(playerCards);
            final Set<CardEntity> collect = playerCards.stream()
                    .map(cardService::findByName)
                    .collect(Collectors.toSet());
            game.setPlayerCards(collect);
            game.setPlayerSum(getSumFromCard(collect));
            game.setPlayerAltSum(getAltSumFromCard(collect));
            game.setGameLoaded(true);
            save(game);
        }
    }


    private int getAltSumFromCard(final Set<CardEntity> cardEntities)
    {
        return cardEntities.stream()
                .map(CardEntity::getValue)
                .map(Integer::parseInt)
                .map(value -> value == 11 ? 1 : value)
                .mapToInt(Integer::intValue)
                .sum();
    }


    private int getSumFromCard(final Set<CardEntity> cardEntities)
    {
        return cardEntities.stream()
                .map(CardEntity::getValue)
                .map(Integer::parseInt)
                .mapToInt(Integer::intValue)
                .sum();
    }


    public GameEntity dealerTurns(final String gameId)
    {
        final GameEntity gameEntity = getGameById(gameId);
        final List<GameStep> stepsToSave = new ArrayList<>();
        if (!gameEntity.isGameFinished())
        {
            final int playerMainSum =
                    gameEntity.getPlayerSum() > 21 ? gameEntity.getPlayerAltSum() : gameEntity.getPlayerSum();
            if (playerMainSum > 22)
            {
                gameEntity.setGameStatus(GameStatus.DEALER_WON);
                gameEntity.setGameFinished(true);
                return gameEntity;
            }
            Integer dealerSum = gameEntity.getDealerSum();
            int dealerAltSum = gameEntity.getDealerAltSum();
            while ((getDealerAverageSum(dealerSum, dealerAltSum, playerMainSum, gameEntity) < 17 || dealerAltSum < 17)
                    && (
                    !(getDealerAverageSum(dealerSum, dealerAltSum, playerMainSum, gameEntity) > playerMainSum)
                            && (getDealerAverageSum(dealerSum, dealerAltSum, playerMainSum, gameEntity) < 21
                            || dealerAltSum < 21)))
            {
                final String nextCard = gameEntity.getDeck()
                        .iterator()
                        .next();
                final CardEntity cardEntity = cardService.findByName(nextCard);
                if (Integer.parseInt(cardEntity.getValue()) == 11)
                {
                    dealerAltSum += 1;
                }
                else
                {
                    dealerAltSum += Integer.parseInt(cardEntity
                            .getValue());
                }
                dealerSum += Integer.parseInt(cardEntity
                        .getValue());
                gameEntity.getDeck()
                        .remove(nextCard);
                gameEntity.getDealerCards()
                        .add(cardEntity);
                final GameStep lastStep = getLastGameStep(gameEntity);
                final GameStep step = new GameStep(gameEntity.getId(), lastStep.getStepNumber() + 1,
                        gameEntity.getDealerCards(), gameEntity.getPlayerCards(), gameEntity.getPlayerSum(),
                        gameEntity.getPlayerAltSum(), recalculateDealerSum(dealerSum, gameEntity), dealerAltSum);
                gameEntity.getGameSteps()
                        .add(step);
                stepsToSave.add(step);
            }
            dealerSum = recalculateDealerSum(dealerSum, gameEntity);
            gameEntity.setGameFinished(true);
            final int dealerMainSum = dealerSum > 21 ? dealerAltSum : dealerSum;
            final boolean dealerBJ = dealerMainSum == 21 && gameEntity.getDealerCards()
                    .size() == 2;
            final boolean playerBj = GameStatus.PLAYER_BJ.equals(
                    gameEntity.getGameStatus());
            if (dealerMainSum < 22 && (dealerMainSum > playerMainSum || (dealerBJ && !playerBj)))
            {
                gameEntity.setGameStatus(GameStatus.DEALER_WON);
            }
            else if (dealerMainSum < 22 && dealerMainSum == playerMainSum && (dealerBJ == playerBj))
            {
                gameEntity.setGameStatus(GameStatus.DRAW);
            }
            else
            {
                gameEntity.setGameStatus(GameStatus.PLAYER_WON);
            }
            gameEntity.setGameFinished(true);
            gameEntity.setDealerAltSum(dealerAltSum);
            gameEntity.setDealerSum(dealerSum);
            stepService.saveAll(stepsToSave);
            save(gameEntity);
        }
        return gameEntity;
    }

    private GameStep getLastGameStep(final GameEntity gameEntity) {
        return gameEntity.getGameSteps()
                .stream()
                .max(Comparator.comparingInt(GameStep::getStepNumber))
                .orElseThrow();
    }


    private Integer recalculateDealerSum(Integer dealerSum, final GameEntity gameEntity)
    {
        final List<CardEntity> cardEntitiesWithAces = gameEntity.getDealerCards()
                .stream()
                .filter(cardEntity -> cardEntity.getValue()
                        .equals("11"))
                .collect(Collectors.toList());
        if (cardEntitiesWithAces.size() > 1)
        {
            for (final CardEntity ignored : cardEntitiesWithAces)
            {
                dealerSum -= 10;
            }
            dealerSum += 10;
        }
        return dealerSum;
    }


    private int getDealerAverageSum(final int dealerSum, final int dealerAltSum, final int playerMainSum,
            final GameEntity gameEntity)
    {
        final int dealerTempSum = recalculateDealerSum(dealerSum, gameEntity);
        return dealerTempSum > 21 ? dealerAltSum : dealerTempSum < playerMainSum ? dealerAltSum : dealerTempSum;
    }


    public GameEntity addCardToPlayer(final String gameId)
    {
        final GameEntity gameEntity = getGameById(gameId);
        if (!gameEntity.isGameFinished() && getSumFromCard(gameEntity.getPlayerCards()) != 21
                && getAltSumFromCard(gameEntity.getPlayerCards()) != 21)
        {
            final String nextCard = gameEntity.getDeck()
                    .iterator()
                    .next();
            gameEntity.getDeck()
                    .remove(nextCard);
            final CardEntity cardEntity = cardService.findByName(nextCard);
            gameEntity.getPlayerCards()
                    .add(cardEntity);
            final int sumFromCard = getSumFromCard(gameEntity.getPlayerCards());
            final int altSumFromCard = getAltSumFromCard(gameEntity.getPlayerCards());
            gameEntity.setPlayerSum(sumFromCard);
            gameEntity.setPlayerAltSum(altSumFromCard);
            if (sumFromCard > 21 && altSumFromCard > 21)
            {
                gameEntity.setGameStatus(GameStatus.DEALER_WON);
                gameEntity.setGameFinished(true);
            }
            final GameStep lastStep = getLastGameStep(gameEntity);
            final GameStep step = new GameStep(gameEntity.getId(), lastStep.getStepNumber() + 1,
                    gameEntity.getDealerCards(), gameEntity.getPlayerCards(), gameEntity.getPlayerSum(),
                    gameEntity.getPlayerAltSum(), gameEntity.getDealerSum(), gameEntity.getDealerAltSum());
            gameEntity.getGameSteps()
                    .add(step);
            stepService.save(step);
            save(gameEntity);
        }
        return gameEntity;
    }

    public GameEntity getGame(final String gameId, final Principal principal) {
        final User user = userService.findMe(principal);
        return gameRepository.findByUserAndId(user, gameId).orElseThrow();
    }
}
