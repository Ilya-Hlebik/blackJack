package com.blackJack.repository;

import com.blackJack.dbo.Bet;
import com.blackJack.dbo.GameEntity;

import java.util.List;

public interface BetRepository  extends AbstractRepository<Bet>{
    List<Bet> findByGameEntityAndProcessedFalse(GameEntity gameEntity);
}
