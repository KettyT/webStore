package ru.tutikova.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.tutikova.dto.SimpleDto;
import ru.tutikova.dto.UserDto;
import ru.tutikova.dto.cart.CartDto;
import ru.tutikova.service.CartService;

@RestController
@RequestMapping("/api/cart/")
public class CartController {

    private CartService cartService;

    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @RequestMapping(value = "addToCart", method = RequestMethod.POST)
    public CartDto addToCart(@RequestBody CartDto dto) {
        return cartService.addToCart(dto);
    }

    @RequestMapping(value = "getCartStatistics", method = RequestMethod.GET)
    public CartDto getCartStatistics () {
        SecurityContext sc = SecurityContextHolder.getContext();
        UserDto userDto = (UserDto)sc.getAuthentication();

        String sessionKey = userDto.getSessionId();
        return cartService.getCartStatistics(sessionKey);
    }
}
