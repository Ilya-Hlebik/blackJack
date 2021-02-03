package com.blackJack.service;


import java.util.List;

import com.blackJack.dbo.CardEntity;
import com.blackJack.repository.CardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class CardService
{
    private final CardRepository cardRepository;


    public CardEntity getCardBack()
    {
        return cardRepository.findByName("CB");
    }


    public List<CardEntity> getDeck()
    {
        return cardRepository.findAll();
    }


    public CardEntity findByName(final String cardName)
    {
        return cardRepository.findByName(cardName);
    }
}

