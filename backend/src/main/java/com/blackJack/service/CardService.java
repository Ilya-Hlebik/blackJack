package com.blackJack.service;


import java.util.List;

import com.blackJack.dbo.CardEntity;
import com.blackJack.repository.CardRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;


@Service
public class CardService extends AbstractService<CardEntity, CardRepository>
{

    public CardService(final CardRepository repository, final ModelMapper modelMapper)
    {
        super(repository, modelMapper);
    }


    public CardEntity getCardBack()
    {
        return repository.findByName("CB");
    }


    public List<CardEntity> getDeck()
    {
        return repository.findAll();
    }


    public CardEntity findByName(final String cardName)
    {
        return repository.findByName(cardName);
    }
}

