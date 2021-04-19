package ru.tiutikova.dto.cart;

import ru.tiutikova.dao.entity.cart.CartDetailsEntity;
import ru.tiutikova.dao.entity.cart.VCartInfoEntity;

import java.math.BigDecimal;

public class VCartInfoDto {
    private int id;
    private int quantity;
    private int storeQuantity;
    private BigDecimal price;
    private int amountInPackage;
    private String itemName;

    private int sessionId;
    private int detailId;
    private int cartId;
    private String sessionCode;

    public VCartInfoDto(VCartInfoEntity entity) {
        this.id = entity.getId();
        this.quantity = entity.getQuantity();
        this.storeQuantity = entity.getStoreQuantity();
        this.price = entity.getPrice();
        this.amountInPackage = entity.getAmountInPackage();
        this.itemName = entity.getItemName();
        this.sessionId = entity.getSessionId();
        this.detailId = entity.getDetailId();
        this.cartId = entity.getCartId();
        this.sessionCode = entity.getSessionCode();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getStoreQuantity() {
        return storeQuantity;
    }

    public void setStoreQuantity(int storeQuantity) {
        this.storeQuantity = storeQuantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getAmountInPackage() {
        return amountInPackage;
    }

    public void setAmountInPackage(int amountInPackage) {
        this.amountInPackage = amountInPackage;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getSessionId() {
        return sessionId;
    }

    public void setSessionId(int sessionId) {
        this.sessionId = sessionId;
    }

    public int getDetailId() {
        return detailId;
    }

    public void setDetailId(int detailId) {
        this.detailId = detailId;
    }

    public int getCartId() {
        return cartId;
    }

    public void setCartId(int cartId) {
        this.cartId = cartId;
    }

    public String getSessionCode() {
        return sessionCode;
    }

    public void setSessionCode(String sessionCode) {
        this.sessionCode = sessionCode;
    }
}
