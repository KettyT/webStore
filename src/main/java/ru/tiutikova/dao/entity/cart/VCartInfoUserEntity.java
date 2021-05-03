package ru.tiutikova.dao.entity.cart;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "v_cart_info_user", schema = "auto_market", catalog = "")
public class VCartInfoUserEntity implements IVCartInfoEntity {
    @Id
    private int id;
    private int quantity;
    private int storeQuantity;
    private BigDecimal price;
    private int amountInPackage;
    private String itemName;
    private int sessionId;
    private int cartId;
    private int detailId;
    private int userId;

    @Basic
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "quantity")
    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Basic
    @Column(name = "store_quantity")
    public int getStoreQuantity() {
        return storeQuantity;
    }

    public void setStoreQuantity(int storeQuantity) {
        this.storeQuantity = storeQuantity;
    }

    @Basic
    @Column(name = "price")
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Basic
    @Column(name = "amount_in_package")
    public int getAmountInPackage() {
        return amountInPackage;
    }

    public void setAmountInPackage(int amountInPackage) {
        this.amountInPackage = amountInPackage;
    }

    @Basic
    @Column(name = "item_name")
    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    @Basic
    @Column(name = "session_id")
    public int getSessionId() {
        return sessionId;
    }

    public void setSessionId(int sessionId) {
        this.sessionId = sessionId;
    }

    @Basic
    @Column(name = "cart_id")
    public int getCartId() {
        return cartId;
    }

    @Override
    public String getSessionCode() {
        return null;
    }

    public void setCartId(int cartId) {
        this.cartId = cartId;
    }

    @Basic
    @Column(name = "detail_id")
    public int getDetailId() {
        return detailId;
    }

    public void setDetailId(int detailId) {
        this.detailId = detailId;
    }

    @Basic
    @Column(name = "user_id")
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VCartInfoUserEntity that = (VCartInfoUserEntity) o;
        return id == that.id &&
                quantity == that.quantity &&
                storeQuantity == that.storeQuantity &&
                amountInPackage == that.amountInPackage &&
                sessionId == that.sessionId &&
                cartId == that.cartId &&
                detailId == that.detailId &&
                userId == that.userId &&
                Objects.equals(price, that.price) &&
                Objects.equals(itemName, that.itemName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, quantity, storeQuantity, price, amountInPackage, itemName, sessionId, cartId, detailId, userId);
    }
}
