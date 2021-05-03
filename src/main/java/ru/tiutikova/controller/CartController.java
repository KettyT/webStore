package ru.tiutikova.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.tiutikova.dto.UserDto;
import ru.tiutikova.dto.cart.CartDto;
import ru.tiutikova.dto.cart.FullCartInfoDto;
import ru.tiutikova.service.CartService;

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
        CartDto cartDto = cartService.getCartStatistics(userDto.getId(), sessionKey);

        if (userDto.getEmail() != null) {
            cartDto.setName(userDto.getName());
            cartDto.setSurname(userDto.getSurname());
        }

        return cartDto;
    }

    @RequestMapping(value = "getFullCartInfo", method = RequestMethod.GET)
    public FullCartInfoDto getFullCartInfo() {
        return cartService.getFullCartInfo();
    }

}
