package com.blackJack.migration;


import com.blackJack.dbo.CardEntity;
import com.blackJack.repository.CardRepository;
import com.kuliginstepan.mongration.annotation.Changelog;
import com.kuliginstepan.mongration.annotation.Changeset;
import org.springframework.transaction.annotation.Transactional;


@Changelog
public class Migration
{

    private final CardRepository cardRepository;


    public Migration(final CardRepository cardRepository)
    {
        this.cardRepository = cardRepository;
    }


    @Transactional
    @Changeset(order = 1, id = "create carts", author = "hlebik")
    public void createCards()
    {
        cardRepository.save(new CardEntity("/backend/storage/card/2C.jpg", "2", "2C"));
        cardRepository.save(new CardEntity("/backend/storage/card/2D.jpg", "2", "2D"));
        cardRepository.save(new CardEntity("/backend/storage/card/2H.jpg", "2", "2H"));
        cardRepository.save(new CardEntity("/backend/storage/card/2S.jpg", "2", "2S"));

        cardRepository.save(new CardEntity("/backend/storage/card/3C.jpg", "3", "3C"));
        cardRepository.save(new CardEntity("/backend/storage/card/3D.jpg", "3", "3D"));
        cardRepository.save(new CardEntity("/backend/storage/card/3H.jpg", "3", "3H"));
        cardRepository.save(new CardEntity("/backend/storage/card/3S.jpg", "3", "3S"));

        cardRepository.save(new CardEntity("/backend/storage/card/4C.jpg", "4", "4C"));
        cardRepository.save(new CardEntity("/backend/storage/card/4D.jpg", "4", "4D"));
        cardRepository.save(new CardEntity("/backend/storage/card/4H.jpg", "4", "4H"));
        cardRepository.save(new CardEntity("/backend/storage/card/4S.jpg", "4", "4S"));

        cardRepository.save(new CardEntity("/backend/storage/card/5C.jpg", "5", "5C"));
        cardRepository.save(new CardEntity("/backend/storage/card/5D.jpg", "5", "5D"));
        cardRepository.save(new CardEntity("/backend/storage/card/5H.jpg", "5", "5H"));
        cardRepository.save(new CardEntity("/backend/storage/card/5S.jpg", "5", "5S"));

        cardRepository.save(new CardEntity("/backend/storage/card/6C.jpg", "6", "6C"));
        cardRepository.save(new CardEntity("/backend/storage/card/6D.jpg", "6", "6D"));
        cardRepository.save(new CardEntity("/backend/storage/card/6H.jpg", "6", "6H"));
        cardRepository.save(new CardEntity("/backend/storage/card/6S.jpg", "6", "6S"));

        cardRepository.save(new CardEntity("/backend/storage/card/7C.jpg", "7", "7C"));
        cardRepository.save(new CardEntity("/backend/storage/card/7D.jpg", "7", "7D"));
        cardRepository.save(new CardEntity("/backend/storage/card/7H.jpg", "7", "7H"));
        cardRepository.save(new CardEntity("/backend/storage/card/7S.jpg", "7", "7S"));

        cardRepository.save(new CardEntity("/backend/storage/card/8C.jpg", "8", "8C"));
        cardRepository.save(new CardEntity("/backend/storage/card/8D.jpg", "8", "8D"));
        cardRepository.save(new CardEntity("/backend/storage/card/8H.jpg", "8", "8H"));
        cardRepository.save(new CardEntity("/backend/storage/card/8S.jpg", "8", "8S"));

        cardRepository.save(new CardEntity("/backend/storage/card/9C.jpg", "9", "9C"));
        cardRepository.save(new CardEntity("/backend/storage/card/9D.jpg", "9", "9D"));
        cardRepository.save(new CardEntity("/backend/storage/card/9H.jpg", "9", "9H"));
        cardRepository.save(new CardEntity("/backend/storage/card/9S.jpg", "9", "9S"));

        cardRepository.save(new CardEntity("/backend/storage/card/10C.jpg", "10", "10C"));
        cardRepository.save(new CardEntity("/backend/storage/card/10D.jpg", "10", "10D"));
        cardRepository.save(new CardEntity("/backend/storage/card/10H.jpg", "10", "10H"));
        cardRepository.save(new CardEntity("/backend/storage/card/10S.jpg", "10", "10S"));

        cardRepository.save(new CardEntity("/backend/storage/card/JC.jpg", "10", "JC"));
        cardRepository.save(new CardEntity("/backend/storage/card/JD.jpg", "10", "JD"));
        cardRepository.save(new CardEntity("/backend/storage/card/JH.jpg", "10", "JH"));
        cardRepository.save(new CardEntity("/backend/storage/card/JS.jpg", "10", "JS"));

        cardRepository.save(new CardEntity("/backend/storage/card/QC.jpg", "10", "QC"));
        cardRepository.save(new CardEntity("/backend/storage/card/QD.jpg", "10", "QD"));
        cardRepository.save(new CardEntity("/backend/storage/card/QH.jpg", "10", "QH"));
        cardRepository.save(new CardEntity("/backend/storage/card/QS.jpg", "10", "QS"));

        cardRepository.save(new CardEntity("/backend/storage/card/KC.jpg", "10", "KC"));
        cardRepository.save(new CardEntity("/backend/storage/card/KD.jpg", "10", "KD"));
        cardRepository.save(new CardEntity("/backend/storage/card/KH.jpg", "10", "KH"));
        cardRepository.save(new CardEntity("/backend/storage/card/KS.jpg", "10", "KS"));

        cardRepository.save(new CardEntity("/backend/storage/card/AC.jpg", "11", "AC"));
        cardRepository.save(new CardEntity("/backend/storage/card/AD.jpg", "11", "AD"));
        cardRepository.save(new CardEntity("/backend/storage/card/AH.jpg", "11", "AH"));
        cardRepository.save(new CardEntity("/backend/storage/card/AS.jpg", "11", "AS"));

        cardRepository.save(new CardEntity("/backend/storage/card/Gray_back.jpg", null, "CB"));
    }

}
