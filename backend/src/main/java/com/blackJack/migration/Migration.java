package com.blackJack.migration;


import com.blackJack.dbo.CartEntity;
import com.blackJack.repository.CartRepository;
import com.kuliginstepan.mongration.annotation.Changelog;
import com.kuliginstepan.mongration.annotation.Changeset;
import org.springframework.transaction.annotation.Transactional;


@Changelog
public class Migration
{

    private final CartRepository cartRepository;


    public Migration(CartRepository cartRepository)
    {
        this.cartRepository = cartRepository;
    }


    @Transactional
    @Changeset(order = 1, id = "create carts", author = "hlebik")
    public void createCards()
    {
        cartRepository.save(new CartEntity("/backend/storage/files/2C.jpg", "2", "2C"));
        cartRepository.save(new CartEntity("/backend/storage/files/2D.jpg", "2", "2D"));
        cartRepository.save(new CartEntity("/backend/storage/files/2H.jpg", "2", "2H"));
        cartRepository.save(new CartEntity("/backend/storage/files/2S.jpg", "2", "2S"));

        cartRepository.save(new CartEntity("/backend/storage/files/3C.jpg", "3", "3C"));
        cartRepository.save(new CartEntity("/backend/storage/files/3D.jpg", "3", "3D"));
        cartRepository.save(new CartEntity("/backend/storage/files/3H.jpg", "3", "3H"));
        cartRepository.save(new CartEntity("/backend/storage/files/3S.jpg", "3", "3S"));

        cartRepository.save(new CartEntity("/backend/storage/files/4C.jpg", "4", "4C"));
        cartRepository.save(new CartEntity("/backend/storage/files/4D.jpg", "4", "4D"));
        cartRepository.save(new CartEntity("/backend/storage/files/4H.jpg", "4", "4H"));
        cartRepository.save(new CartEntity("/backend/storage/files/4S.jpg", "4", "4S"));

        cartRepository.save(new CartEntity("/backend/storage/files/5C.jpg", "5", "5C"));
        cartRepository.save(new CartEntity("/backend/storage/files/5D.jpg", "5", "5D"));
        cartRepository.save(new CartEntity("/backend/storage/files/5H.jpg", "5", "5H"));
        cartRepository.save(new CartEntity("/backend/storage/files/5S.jpg", "5", "5S"));

        cartRepository.save(new CartEntity("/backend/storage/files/6C.jpg", "6", "6C"));
        cartRepository.save(new CartEntity("/backend/storage/files/6D.jpg", "6", "6D"));
        cartRepository.save(new CartEntity("/backend/storage/files/6H.jpg", "6", "6H"));
        cartRepository.save(new CartEntity("/backend/storage/files/6S.jpg", "6", "6S"));

        cartRepository.save(new CartEntity("/backend/storage/files/7C.jpg", "7", "7C"));
        cartRepository.save(new CartEntity("/backend/storage/files/7D.jpg", "7", "7D"));
        cartRepository.save(new CartEntity("/backend/storage/files/7H.jpg", "7", "7H"));
        cartRepository.save(new CartEntity("/backend/storage/files/7S.jpg", "7", "7S"));

        cartRepository.save(new CartEntity("/backend/storage/files/8C.jpg", "8", "8C"));
        cartRepository.save(new CartEntity("/backend/storage/files/8D.jpg", "8", "8D"));
        cartRepository.save(new CartEntity("/backend/storage/files/8H.jpg", "8", "8H"));
        cartRepository.save(new CartEntity("/backend/storage/files/8S.jpg", "8", "8S"));

        cartRepository.save(new CartEntity("/backend/storage/files/9C.jpg", "9", "9C"));
        cartRepository.save(new CartEntity("/backend/storage/files/9D.jpg", "9", "9D"));
        cartRepository.save(new CartEntity("/backend/storage/files/9H.jpg", "9", "9H"));
        cartRepository.save(new CartEntity("/backend/storage/files/9S.jpg", "9", "9S"));

        cartRepository.save(new CartEntity("/backend/storage/files/10C.jpg", "10", "10C"));
        cartRepository.save(new CartEntity("/backend/storage/files/10D.jpg", "10", "10D"));
        cartRepository.save(new CartEntity("/backend/storage/files/10H.jpg", "10", "10H"));
        cartRepository.save(new CartEntity("/backend/storage/files/10S.jpg", "10", "10S"));

        cartRepository.save(new CartEntity("/backend/storage/files/JC.jpg", "10", "JC"));
        cartRepository.save(new CartEntity("/backend/storage/files/JD.jpg", "10", "JD"));
        cartRepository.save(new CartEntity("/backend/storage/files/JH.jpg", "10", "JH"));
        cartRepository.save(new CartEntity("/backend/storage/files/JS.jpg", "10", "JS"));

        cartRepository.save(new CartEntity("/backend/storage/files/QC.jpg", "10", "QC"));
        cartRepository.save(new CartEntity("/backend/storage/files/QD.jpg", "10", "QD"));
        cartRepository.save(new CartEntity("/backend/storage/files/QH.jpg", "10", "QH"));
        cartRepository.save(new CartEntity("/backend/storage/files/QS.jpg", "10", "QS"));

        cartRepository.save(new CartEntity("/backend/storage/files/KC.jpg", "10", "KC"));
        cartRepository.save(new CartEntity("/backend/storage/files/KD.jpg", "10", "KD"));
        cartRepository.save(new CartEntity("/backend/storage/files/KH.jpg", "10", "KH"));
        cartRepository.save(new CartEntity("/backend/storage/files/KS.jpg", "10", "KS"));

        cartRepository.save(new CartEntity("/backend/storage/files/AC.jpg", "11", "AC"));
        cartRepository.save(new CartEntity("/backend/storage/files/AD.jpg", "11", "AD"));
        cartRepository.save(new CartEntity("/backend/storage/files/AH.jpg", "11", "AH"));
        cartRepository.save(new CartEntity("/backend/storage/files/AS.jpg", "11", "AS"));

        cartRepository.save(new CartEntity("/backend/storage/files/Gray_back.jpg", null, "CB"));
    }

}
