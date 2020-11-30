package com.blackJack.service;

import com.blackJack.dbo.Bet;
import com.blackJack.dbo.User;
import com.blackJack.enumeration.BetStatus;
import com.blackJack.repository.BetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
@RequiredArgsConstructor
public class BetService {
    private final BetRepository betRepository;
    private final UserService userService;
    private final GameService gameService;
    private final UserInfoService userInfoService;

    public Bet getBetByID(final String betID) {
        return betRepository.findById(betID).orElseThrow();
    }

    public ResponseEntity<Void> placeBet(final Principal principal, final double amount, final String gameId) {

        final User me = userService.findMe(principal);
        if (me.getUserInfo() != null) {
            final double depositSum = me.getUserInfo().getDepositSum();
            if (depositSum <= amount) {
                final Bet bet = new Bet();
                bet.setBetStatus(BetStatus.PLACED);
                bet.setGameEntity(gameService.getGameById(gameId));
                bet.setAmount(amount);
                betRepository.save(bet);
                me.getUserInfo().setDepositSum(depositSum - amount);
                userInfoService.save(me.getUserInfo());
            }
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
}
