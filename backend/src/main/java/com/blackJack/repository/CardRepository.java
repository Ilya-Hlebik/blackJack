package com.blackJack.repository;


import com.blackJack.dbo.CardEntity;


public interface CardRepository extends AbstractRepository<CardEntity>
{
    CardEntity findByName(String name);
}
