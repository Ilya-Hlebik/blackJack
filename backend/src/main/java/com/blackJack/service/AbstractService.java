package com.blackJack.service;

import com.blackJack.dbo.AbstractEntity;
import com.blackJack.repository.AbstractRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;

@AllArgsConstructor
public abstract class AbstractService<EntityClass extends AbstractEntity> {
    protected final AbstractRepository<EntityClass> repository;
    protected final ModelMapper modelMapper;
}
