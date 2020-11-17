package com.blackJack.repository;


import com.blackJack.dbo.GameEntity;
import com.blackJack.dbo.User;

import java.util.Optional;


public interface GameRepository extends AbstractRepository<GameEntity>
{
    Optional<GameEntity> findByUserAndId(User user,String id);
}
