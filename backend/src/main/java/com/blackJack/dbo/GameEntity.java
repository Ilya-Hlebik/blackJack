package com.blackJack.dbo;


import java.util.Set;

import com.blackJack.enumeration.GameStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "GAME")
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class GameEntity extends AbstractEntity
{
    @JsonIgnore
    private Set<String> deck;

    private Set<CardEntity> playerCards;

    private Set<CardEntity> dealerCards;

    private int playerSum;

    private int playerAltSum;

    private int dealerSum;

    private int dealerAltSum;

    private boolean gameLoaded;

    private GameStatus gameStatus;

    private boolean gameFinished;
}
