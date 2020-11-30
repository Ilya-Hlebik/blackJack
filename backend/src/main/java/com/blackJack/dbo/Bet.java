package com.blackJack.dbo;

import com.blackJack.enumeration.BetStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "BET")
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class Bet extends AbstractEntity {
    private double amount;
    private BetStatus betStatus;
    @DBRef
    private GameEntity gameEntity;
}
