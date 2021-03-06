package com.blackJack.service;


import java.security.Principal;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.blackJack.dbo.CardEntity;
import com.blackJack.dbo.GameEntity;
import com.blackJack.dbo.GameStep;
import com.blackJack.dbo.User;
import com.blackJack.enumeration.GameStatus;
import com.blackJack.repository.GameRepository;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class GameService extends AbstractService<GameEntity, GameRepository>
{
    private final CardService cardService;

    private final StepService stepService;

    private final UserService userService;

    private final BetService betService;

    private final LogService logService;


    public GameService(final GameRepository repository, final ModelMapper modelMapper,
            final CardService cardService, final StepService stepService, final UserService userService,
            @Lazy final BetService betService, final LogService logService)
    {
        super(repository, modelMapper);
        this.cardService = cardService;
        this.stepService = stepService;
        this.userService = userService;
        this.betService = betService;
        this.logService = logService;
    }


    public String createGame(final Principal req)
    {
        final List<String> deck = cardService.getDeck()
                .stream()
                .map(CardEntity::getName)
                .filter(s -> !"CB".equals(s))
                .collect(Collectors.toList());
        Collections.shuffle(deck);
        final User user = userService.findMe(req);
        return repository.save(
                new GameEntity(new LinkedHashSet<>(deck), Collections.emptySet(), Collections.emptySet(), 0, 0, 0, 0,
                        false,
                        GameStatus.IN_PROGRESS, false,user, Collections.emptyList(), Collections.emptyList()))
                .getId();
    }


    public GameEntity getGameById(final String gameId)
    {
        return repository.findById(gameId)
                .orElseThrow();
    }


    public void save(final GameEntity game)
    {
        repository.save(game);
    }


    public GameEntity getGameWithResults(final String gameId)
    {
        getDealerCards(gameId);
        getPlayerCards(gameId);
        final GameEntity gameEntity = getGameById(gameId);
        final GameStep step = new GameStep(gameEntity.getId(), 0, gameEntity.getPlayerSum(),
                gameEntity.getPlayerAltSum(),
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
            game.setDealerSum(getSumFromCard(Set.of(cardEntity)));
            game.setDealerAltSum(getAltSumFromCard(Set.of(cardEntity)));
            save(game);
            logService.saveLog(game, "Dealer card: " + cardEntity.getName() +
                    " dealer sum: " + game.getDealerSum() + " dealer alt sum: " + game.getDealerAltSum());
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
            logService.saveLog(game,
                    "Player cards: " + collect.stream().map(CardEntity::getName).collect(Collectors.joining(",")) +
                            " player sum: " + game.getPlayerSum() + " player alt sum: " + game.getPlayerAltSum());
        }
    }


    private int getAltSumFromCard(final Set<CardEntity> cardEntities)
    {
        final List<CardEntity> cardEntitiesWithAces = cardEntities
                .stream()
                .filter(cardEntity -> cardEntity.getValue()
                        .equals("11"))
                .collect(Collectors.toList());
        int sum = cardEntities.stream()
                .map(CardEntity::getValue)
                .map(Integer::parseInt)
                .map(value -> value == 11 ? 1 : value)
                .mapToInt(Integer::intValue)
                .sum();
        if (cardEntitiesWithAces.size() > 1 && sum + 10 <= 21)
        {
            sum += 10;
        }
        return sum;
    }


    private int getSumFromCard(final Set<CardEntity> cardEntities)
    {
        return cardEntities.stream()
                .map(CardEntity::getValue)
                .map(Integer::parseInt)
                .mapToInt(Integer::intValue)
                .sum();
    }


    public GameEntity dealerTurns(final Principal principal, final String gameId)
    {
        final GameEntity gameEntity = getGameById(gameId);
        if (!gameEntity.isGameFinished())
        {
            final int playerMainSum =
                    gameEntity.getPlayerSum() > 21 ? gameEntity.getPlayerAltSum() : gameEntity.getPlayerSum();
            if (playerMainSum > 22)
            {
                gameEntity.setGameStatus(GameStatus.DEALER_WON);
                gameEntity.setGameFinished(true);
                betService.calculateBets(principal, gameEntity);
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
                        gameEntity.getPlayerSum(),
                        gameEntity.getPlayerAltSum(), recalculateDealerSum(dealerSum, gameEntity), dealerAltSum);
                final GameStep savedStep = stepService.save(step);
                gameEntity.getGameSteps()
                        .add(savedStep);
                logService.saveLog(gameEntity,
                        "Dealer hit with: " + cardEntity.getName() +
                                " dealer sum: " + dealerSum + " dealer alt sum: " + dealerAltSum);
            }
            dealerSum = recalculateDealerSum(dealerSum, gameEntity);
            final int dealerMainSum = dealerSum > 21 ? dealerAltSum : dealerSum;
            logService.saveLog(gameEntity,
                    "Dealer final sum: " + dealerMainSum);
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
            else if(!playerBj)
            {
                gameEntity.setGameStatus(GameStatus.PLAYER_WON);
            }
            gameEntity.setGameFinished(true);
            gameEntity.setDealerAltSum(dealerAltSum);
            gameEntity.setDealerSum(dealerSum);
            logService.saveLog(gameEntity,
                    "Game ended with result: " + gameEntity.getGameStatus());
            save(gameEntity);
            betService.calculateBets(principal, gameEntity);
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


    public GameEntity addCardToPlayer(final Principal principal, final String gameId)
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
            logService.saveLog(gameEntity,
                    "Player hit with: " + cardEntity.getName() +
                            " player sum: " + gameEntity.getPlayerSum() + " player alt sum: " + gameEntity
                            .getPlayerAltSum());
            if (sumFromCard > 21 && altSumFromCard > 21)
            {
                gameEntity.setGameStatus(GameStatus.DEALER_WON);
                gameEntity.setGameFinished(true);
                betService.calculateBets(principal, gameEntity);
            }
            final GameStep lastStep = getLastGameStep(gameEntity);
            final GameStep step = new GameStep(gameEntity.getId(), lastStep.getStepNumber() + 1,
                    gameEntity.getPlayerSum(),
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
        return repository.findByUserAndId(user, gameId).orElseThrow();
    }
}
