package ru.tiutikova.dto.cart;

import java.util.ArrayList;
import java.util.List;

public class FullCartInfoDto extends CartDto {

    private List<VCartInfoDto> cartDetailsDtooList = new ArrayList<>();

    public List<VCartInfoDto> getCartDetailsDtooList() {
        return cartDetailsDtooList;
    }

    public void setCartDetailsDtooList(List<VCartInfoDto> cartDetailsDtooList) {
        this.cartDetailsDtooList = cartDetailsDtooList;
    }
}
