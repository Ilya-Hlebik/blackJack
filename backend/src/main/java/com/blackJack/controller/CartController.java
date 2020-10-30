package com.blackJack.controller;


import java.util.List;

import com.blackJack.dbo.CartEntity;
import com.blackJack.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("/cart")
public class CartController
{
    private final CartService cartService;


    @GetMapping("/dealer")
    public List<CartEntity> getDealerCard()
    {
        return cartService.getDealerCards();
    }


    @GetMapping("/player")
    public List<CartEntity> getPlayerCards()
    {
        return cartService.getPlayerCards();
    }
}
