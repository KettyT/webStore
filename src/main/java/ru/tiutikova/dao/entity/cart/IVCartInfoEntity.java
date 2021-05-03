package ru.tiutikova.dao.entity.cart;

import java.math.BigDecimal;

public interface IVCartInfoEntity {

    int getId();
    int getQuantity();
    int getStoreQuantity();

    BigDecimal getPrice();
    int getAmountInPackage();
    String getItemName();
    int getDetailId();
    int getSessionId();
    int getCartId();
    String getSessionCode();
}
