package com.blackJack.service;


import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.blackJack.dbo.CardEntity;
import com.blackJack.dbo.GameEntity;
import com.blackJack.enumeration.GameStatus;
import com.blackJack.repository.GameRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class GameService
{
    private final CardService cardService;

    private final GameRepository gameRepository;


    public String createGame()
    {
        final List<String> deck = cardService.getDeck()
                .stream()
                .map(CardEntity::getName)
                .filter(s -> !"CB".equals(s))
                .collect(Collectors.toList());
        Collections.shuffle(deck);

        return gameRepository.save(
                new GameEntity(new LinkedHashSet<>(deck), Collections.emptySet(), Collections.emptySet(), 0, 0, false,
                        GameStatus.IN_PROGRESS, false))
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
        if (GameStatus.IN_PROGRESS.equals(gameEntity.getGameStatus()) && gameEntity.getPlayerCards()
                .size() == 2 && gameEntity.getPlayerSum() == 21)
        {
            gameEntity.setGameStatus(GameStatus.PLAYER_BJ);
            gameRepository.save(gameEntity);
        }
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
            game.setDealerSum(Integer.parseInt(cardEntity.getValue()));
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
            game.setGameLoaded(true);
            save(game);
        }
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
        if (!gameEntity.isGameFinished())
        {
            final int playerSum = gameEntity.getPlayerSum();
            if (playerSum > 22)
            {
                gameEntity.setGameStatus(GameStatus.DEALER_WON);
                gameEntity.setGameFinished(true);
                return gameEntity;
            }
            int dealerSum = gameEntity.getDealerSum();
            while (dealerSum < 17)
            {
                final String nextCard = gameEntity.getDeck()
                        .iterator()
                        .next();
                final CardEntity cardEntity = cardService.findByName(nextCard);
                dealerSum += Integer.parseInt(cardEntity
                        .getValue());
                gameEntity.getDeck()
                        .remove(nextCard);
                gameEntity.getDealerCards()
                        .add(cardEntity);
            }
            gameEntity.setGameFinished(true);

            final boolean dealerBJ = dealerSum == 21 && gameEntity.getDealerCards()
                    .size() == 2;
            final boolean playerBj = GameStatus.PLAYER_BJ.equals(
                    gameEntity.getGameStatus());
            if (dealerSum < 22 && (dealerSum > playerSum || (dealerBJ && !playerBj)))
            {
                gameEntity.setGameStatus(GameStatus.DEALER_WON);
            }
            else if (dealerSum < 22 && dealerSum == playerSum && (dealerBJ == playerBj))
            {
                gameEntity.setGameStatus(GameStatus.DRAW);
            }
            else
            {
                gameEntity.setGameStatus(GameStatus.PLAYER_WON);
            }
            gameEntity.setGameFinished(true);
            gameEntity.setDealerSum(dealerSum);
            save(gameEntity);
        }
        return gameEntity;
    }


    public GameEntity addCardToPlayer(final String gameId)
    {
        final GameEntity gameEntity = getGameById(gameId);
        if (!gameEntity.isGameFinished() && getSumFromCard(gameEntity.getPlayerCards()) != 21)
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
            gameEntity.setPlayerSum(sumFromCard);
            if (sumFromCard > 21)
            {
                gameEntity.setGameStatus(GameStatus.DEALER_WON);
                gameEntity.setGameFinished(true);
            }
            save(gameEntity);
        }
        return gameEntity;
    }
}
