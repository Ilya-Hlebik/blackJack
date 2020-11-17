package com.blackJack.controller;


import com.blackJack.dbo.GameEntity;
import com.blackJack.service.GameService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;


@RestController
@RequiredArgsConstructor
@RequestMapping("/game")
public class GameController
{
    private final GameService gameService;

    @GetMapping("/new")
    public String createGame(final Principal principal){
        return gameService.createGame(principal);
    }

    @GetMapping("/get/{gameId}")
    public GameEntity getGameWithResults(@PathVariable final String gameId){
        return gameService.getGameWithResults(gameId);
    }

    @GetMapping("/{gameId}")
    public GameEntity getGame(final Principal principal, @PathVariable final String gameId){
        return gameService.getGame(gameId, principal);
    }

    @PostMapping("/dealerTurns/{gameId}")
    public GameEntity dealerTurns(@PathVariable final String gameId){
        return  gameService.dealerTurns(gameId);
    }

    @PostMapping("/addCardToPlayer/{gameId}")
    public GameEntity addCardToPlayer(@PathVariable final String gameId){
        return  gameService.addCardToPlayer(gameId);
    }
}
