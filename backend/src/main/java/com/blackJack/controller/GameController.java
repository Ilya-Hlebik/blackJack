package com.blackJack.controller;


import com.blackJack.dbo.GameEntity;
import com.blackJack.service.GameService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/game")
public class GameController
{
    private final GameService gameService;

    @GetMapping("/new")
    public String createGame(){
        return gameService.createGame();
    }

    @GetMapping("/get/{gameId}")
    public GameEntity getGameWithResults(@PathVariable final String gameId){
        return gameService.getGameWithResults(gameId);
    }

    @GetMapping("/{gameId}")
    public GameEntity getGame(@PathVariable final String gameId){
        return gameService.getGame(gameId);
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
