package com.blackJack.repository;


import com.blackJack.dbo.AbstractEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.NoRepositoryBean;


@NoRepositoryBean
public interface AbstractRepository<EntityClass extends AbstractEntity> extends MongoRepository<EntityClass, String>
{
}
