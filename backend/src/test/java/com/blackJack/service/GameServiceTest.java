package com.blackJack.service;


import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.blackJack.dbo.CardEntity;
import com.blackJack.dbo.GameEntity;
import com.blackJack.enumeration.GameStatus;
import com.blackJack.repository.GameRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.junit4.SpringRunner;


@SpringBootTest
@RunWith(SpringRunner.class)
public class GameServiceTest
{

    public static final String GAME_ID = "1L";

    @MockBean
    private  GameRepository gameRepository;

    @SpyBean
    private GameService gameService;

    @MockBean
    private CardService cardService;

    @Test
    public void dealerTurnsA()
    {
        final GameEntity gameEntity = new GameEntity();
        gameEntity.setDeck(new LinkedHashSet<>(Arrays.asList("10c","4C", "2C")));
        gameEntity.setDealerAltSum(1);
        gameEntity.setDealerSum(11);
        final CardEntity cardEntity = new CardEntity();
        cardEntity.setName("AC");
        cardEntity.setValue("11");
        final Set<CardEntity> cardEntityList = new HashSet<>();
        cardEntityList.add(cardEntity);
        gameEntity.setDealerCards(cardEntityList);

        gameEntity.setPlayerSum(20);
        gameEntity.setPlayerAltSum(20);


        final CardEntity dealerCart2 = new CardEntity();
        dealerCart2.setValue("10");
        dealerCart2.setName("10c");
        Mockito.doReturn(dealerCart2).when(cardService).findByName("10c");

        final CardEntity dealerCart3 = new CardEntity();
        dealerCart3.setValue("4");
        dealerCart3.setName("4C");
        Mockito.doReturn(dealerCart3).when(cardService).findByName("4C");

        final CardEntity dealerCart4 = new CardEntity();
        dealerCart4.setValue("2");
        dealerCart4.setName("2C");
        Mockito.doReturn(dealerCart4).when(cardService).findByName("2C");

        Mockito.doReturn(Optional.of(gameEntity)).when(gameRepository).findById(GAME_ID);
        final GameEntity gameResult = gameService.dealerTurns(GAME_ID);
        Assert.assertEquals(GameStatus.DEALER_WON,gameEntity.getGameStatus());
        Assert.assertEquals(2,gameResult.getDealerCards().size());
        Assert.assertEquals(21,gameResult.getDealerSum());
    }

    @Test
    public void dealerTurnsB()
    {
        final GameEntity gameEntity = new GameEntity();
        gameEntity.setDeck(new LinkedHashSet<>(Arrays.asList("AD","4C", "2C")));
        gameEntity.setDealerAltSum(1);
        gameEntity.setDealerSum(11);
        final CardEntity cardEntity = new CardEntity();
        cardEntity.setName("AC");
        cardEntity.setValue("11");
        final Set<CardEntity> cardEntityList = new HashSet<>();
        cardEntityList.add(cardEntity);
        gameEntity.setDealerCards(cardEntityList);

        gameEntity.setPlayerSum(17);
        gameEntity.setPlayerAltSum(17);


        final CardEntity dealerCart2 = new CardEntity();
        dealerCart2.setValue("11");
        dealerCart2.setName("AD");
        Mockito.doReturn(dealerCart2).when(cardService).findByName("AD");

        final CardEntity dealerCart3 = new CardEntity();
        dealerCart3.setValue("4");
        dealerCart3.setName("4C");
        Mockito.doReturn(dealerCart3).when(cardService).findByName("4C");

        final CardEntity dealerCart4 = new CardEntity();
        dealerCart4.setValue("2");
        dealerCart4.setName("2C");
        Mockito.doReturn(dealerCart4).when(cardService).findByName("2C");

        Mockito.doReturn(Optional.of(gameEntity)).when(gameRepository).findById(GAME_ID);
        final GameEntity gameResult = gameService.dealerTurns(GAME_ID);
        Assert.assertEquals(GameStatus.DEALER_WON,gameEntity.getGameStatus());
        Assert.assertEquals(4,gameResult.getDealerCards().size());
        Assert.assertEquals(18,gameResult.getDealerSum());
        Assert.assertEquals(8,gameResult.getDealerAltSum());
    }

    @Test
    public void dealerTurnsC()
    {
        final GameEntity gameEntity = new GameEntity();
        gameEntity.setDeck(new LinkedHashSet<>(Arrays.asList("2C","9C", "8C")));
        gameEntity.setDealerAltSum(7);
        gameEntity.setDealerSum(7);
        final CardEntity cardEntity = new CardEntity();
        cardEntity.setName("7C");
        cardEntity.setValue("7");
        final Set<CardEntity> cardEntityList = new HashSet<>();
        cardEntityList.add(cardEntity);
        gameEntity.setDealerCards(cardEntityList);

        gameEntity.setPlayerSum(19);
        gameEntity.setPlayerAltSum(19);


        final CardEntity dealerCart2 = new CardEntity();
        dealerCart2.setValue("2");
        dealerCart2.setName("2C");
        Mockito.doReturn(dealerCart2).when(cardService).findByName("2C");

        final CardEntity dealerCart3 = new CardEntity();
        dealerCart3.setValue("9");
        dealerCart3.setName("9C");
        Mockito.doReturn(dealerCart3).when(cardService).findByName("9C");

        final CardEntity dealerCart4 = new CardEntity();
        dealerCart4.setValue("8");
        dealerCart4.setName("8C");
        Mockito.doReturn(dealerCart4).when(cardService).findByName("8C");

        Mockito.doReturn(Optional.of(gameEntity)).when(gameRepository).findById(GAME_ID);
        final GameEntity gameResult = gameService.dealerTurns(GAME_ID);
        Assert.assertEquals(3,gameResult.getDealerCards().size());
        Assert.assertEquals(18,gameResult.getDealerSum());
        Assert.assertEquals(18,gameResult.getDealerAltSum());
        Assert.assertEquals(GameStatus.PLAYER_WON,gameEntity.getGameStatus());
    }

    @Test
    public void dealerTurnsD()
    {
        final GameEntity gameEntity = new GameEntity();
        final List<String> strings = Arrays.asList("3C", "AC", "AD", "2D", "2C", "7C","3D");
        gameEntity.setDeck(new LinkedHashSet<>(strings));
        gameEntity.setDealerAltSum(2);
        gameEntity.setDealerSum(2);
        final CardEntity cardEntity = new CardEntity();
        cardEntity.setName("2D");
        cardEntity.setValue("2");
        final Set<CardEntity> cardEntityList = new HashSet<>();
        cardEntityList.add(cardEntity);
        gameEntity.setDealerCards(cardEntityList);

        gameEntity.setPlayerSum(19);
        gameEntity.setPlayerAltSum(19);

        final CardEntity dealerCart2 = new CardEntity();
        dealerCart2.setValue("3");
        dealerCart2.setName("3C");
        Mockito.doReturn(dealerCart2).when(cardService).findByName("3C");

        final CardEntity dealerCart3 = new CardEntity();
        dealerCart3.setValue("2");
        dealerCart3.setName("2C");
        Mockito.doReturn(dealerCart3).when(cardService).findByName("2C");

        final CardEntity dealerCart4 = new CardEntity();
        dealerCart4.setValue("11");
        dealerCart4.setName("AC");
        Mockito.doReturn(dealerCart4).when(cardService).findByName("AC");

        final CardEntity dealerCart5 = new CardEntity();
        dealerCart5.setValue("11");
        dealerCart5.setName("AD");
        Mockito.doReturn(dealerCart5).when(cardService).findByName("AD");

        final CardEntity dealerCart6 = new CardEntity();
        dealerCart6.setValue("2");
        dealerCart6.setName("2D");
        Mockito.doReturn(dealerCart6).when(cardService).findByName("2D");

        final CardEntity dealerCart7 = new CardEntity();
        dealerCart7.setValue("7");
        dealerCart7.setName("7C");
        Mockito.doReturn(dealerCart7).when(cardService).findByName("7C");

        final CardEntity dealerCart8 = new CardEntity();
        dealerCart8.setValue("3");
        dealerCart8.setName("3D");
        Mockito.doReturn(dealerCart8).when(cardService).findByName("3D");


        Mockito.doReturn(Optional.of(gameEntity)).when(gameRepository).findById(GAME_ID);

        final GameEntity gameResult = gameService.dealerTurns(GAME_ID);

        Assert.assertEquals(5,gameResult.getDealerCards().size());
        Assert.assertEquals(21,gameResult.getDealerSum());
        Assert.assertEquals(11,gameResult.getDealerAltSum());
        Assert.assertEquals(GameStatus.DEALER_WON,gameEntity.getGameStatus());
    }

    @Test
    public void dealerTurnsE()
    {
        final GameEntity gameEntity = new GameEntity();
        final List<String> strings = Arrays.asList("3C", "AC", "AD", "5D", "4C","5H");
        gameEntity.setDeck(new LinkedHashSet<>(strings));
        gameEntity.setDealerAltSum(2);
        gameEntity.setDealerSum(2);
        final CardEntity cardEntity = new CardEntity();
        cardEntity.setName("2D");
        cardEntity.setValue("2");
        final Set<CardEntity> cardEntityList = new HashSet<>();
        cardEntityList.add(cardEntity);
        gameEntity.setDealerCards(cardEntityList);

        gameEntity.setPlayerSum(19);
        gameEntity.setPlayerAltSum(19);

        final CardEntity dealerCart2 = new CardEntity();
        dealerCart2.setValue("3");
        dealerCart2.setName("3C");
        Mockito.doReturn(dealerCart2).when(cardService).findByName("3C");

        final CardEntity dealerCart3 = new CardEntity();
        dealerCart3.setValue("2");
        dealerCart3.setName("2C");
        Mockito.doReturn(dealerCart3).when(cardService).findByName("2C");

        final CardEntity dealerCart4 = new CardEntity();
        dealerCart4.setValue("11");
        dealerCart4.setName("AC");
        Mockito.doReturn(dealerCart4).when(cardService).findByName("AC");

        final CardEntity dealerCart5 = new CardEntity();
        dealerCart5.setValue("11");
        dealerCart5.setName("AD");
        Mockito.doReturn(dealerCart5).when(cardService).findByName("AD");

        final CardEntity dealerCart6 = new CardEntity();
        dealerCart6.setValue("5");
        dealerCart6.setName("5D");
        Mockito.doReturn(dealerCart6).when(cardService).findByName("5D");

        final CardEntity dealerCart7 = new CardEntity();
        dealerCart7.setValue("4");
        dealerCart7.setName("4C");
        Mockito.doReturn(dealerCart7).when(cardService).findByName("4C");

        final CardEntity dealerCart8 = new CardEntity();
        dealerCart8.setValue("3");
        dealerCart8.setName("3D");
        Mockito.doReturn(dealerCart8).when(cardService).findByName("3D");

        final CardEntity dealerCart9 = new CardEntity();
        dealerCart9.setValue("5");
        dealerCart9.setName("5H");
        Mockito.doReturn(dealerCart9).when(cardService).findByName("5H");


        Mockito.doReturn(Optional.of(gameEntity)).when(gameRepository).findById(GAME_ID);

        final GameEntity gameResult = gameService.dealerTurns(GAME_ID);

        Assert.assertEquals(7,gameResult.getDealerCards().size());
        Assert.assertEquals(31,gameResult.getDealerSum());
        Assert.assertEquals(21,gameResult.getDealerAltSum());
        Assert.assertEquals(GameStatus.DEALER_WON,gameEntity.getGameStatus());
    }
}