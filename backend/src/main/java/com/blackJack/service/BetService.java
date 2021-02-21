package com.blackJack.service;


import java.security.Principal;
import java.util.List;

import com.blackJack.dbo.Bet;
import com.blackJack.dbo.GameEntity;
import com.blackJack.dbo.User;
import com.blackJack.dbo.UserInfo;
import com.blackJack.dto.PlaceDto;
import com.blackJack.enumeration.BetStatus;
import com.blackJack.enumeration.GameStatus;
import com.blackJack.repository.BetRepository;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


@Service
public class BetService extends AbstractService<Bet, BetRepository>
{
    private final UserService userService;

    private final GameService gameService;

    private final UserInfoService userInfoService;

    private final LogService logService;


    public BetService(final BetRepository repository, final ModelMapper modelMapper, final UserService userService,
            @Lazy final GameService gameService, final UserInfoService userInfoService, final LogService logService)
    {
        super(repository, modelMapper);
        this.userService = userService;
        this.gameService = gameService;
        this.userInfoService = userInfoService;
        this.logService = logService;
    }


    public Bet getBetByID(final String betID) {
        return repository.findById(betID).orElseThrow();
    }


    public ResponseEntity<Void> placeBet(final Principal principal, final PlaceDto placeDto) {
        final User me = userService.findMe(principal);
        final GameEntity gameById = gameService.getGameById(placeDto.getGameId());
        if (me.getUserInfo() != null && !gameById.isGameLoaded()) {
            final double depositSum = me.getUserInfo().getDepositSum();
            if (placeDto.getBetSum() != 0 && depositSum >= placeDto.getBetSum()) {
                final Bet bet = new Bet();
                bet.setBetStatus(BetStatus.PLACED);
                bet.setGameEntity(gameById);
                bet.setAmount(placeDto.getBetSum());
                repository.save(bet);
                me.getUserInfo().setDepositSum(depositSum - placeDto.getBetSum());
                userInfoService.save(me.getUserInfo());
                logService.saveLog(gameById, "Place bet with amount " + bet.getAmount() + " EUR");
                return ResponseEntity.status(HttpStatus.OK).build();
            }
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    public void calculateBets(final Principal principal, final GameEntity gameEntity) {
        final List<Bet> byGameEntity = repository.findByGameEntityAndProcessedFalse(gameEntity);
        final User user = userService.findMe(principal);
        byGameEntity.forEach(bet -> {
            final GameStatus gameStatus = gameEntity.getGameStatus();
            if (GameStatus.DRAW == gameStatus) {
                payoutBet(user, bet, 1, gameEntity);
            } else if (GameStatus.PLAYER_WON == gameStatus) {
                payoutBet(user, bet, 2, gameEntity);
            } else if (GameStatus.PLAYER_BJ == gameStatus) {
                payoutBet(user, bet, 3, gameEntity);
            }
            else if (GameStatus.DEALER_WON == gameStatus)
            {
                logService.saveLog(gameEntity,
                        "Game ended with result: " + gameEntity.getGameStatus() + ". No bets will be payout");
            }
            else
            {
                throw new RuntimeException();
            }
        });
    }

    private void payoutBet(final User me, final Bet bet, final int betMultiplier, final GameEntity gameEntity) {
        final UserInfo userInfo = me.getUserInfo();
        userInfo.setDepositSum(userInfo.getDepositSum() + bet.getAmount() * betMultiplier);
        userInfoService.save(userInfo);
        bet.setProcessed(true);
        repository.save(bet);
        logService.saveLog(gameEntity,
                "Payout bet in amount: " + bet.getAmount() * betMultiplier + " EUR");
    }
}
