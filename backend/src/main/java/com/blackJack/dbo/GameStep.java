package com.blackJack.dbo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "GAME_STEPS")
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class GameStep extends AbstractEntity {

    private String gameId;

    private int stepNumber;

    private int playerSum;

    private int playerAltSum;

    private int dealerSum;

    private int dealerAltSum;
}
