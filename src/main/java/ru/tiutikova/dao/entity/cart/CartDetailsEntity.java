package ru.tiutikova.dao.entity.cart;

import javax.persistence.*;

@Entity
@Table(name = "cart_details", schema = "auto_market", catalog = "")
public class CartDetailsEntity {
    private int id;
    private int quantity;

    private int cartId;

    private int storeDetailId;

    @Id
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

    @Column(name = "cart_id")
    public int getCartId() {
        return cartId;
    }

    public void setCartId(int cartId) {
        this.cartId = cartId;
    }

    @Column(name = "stored_detail_id")
    public int getStoreDetailId() {
        return storeDetailId;
    }

    public void setStoreDetailId(int storeDetailId) {
        this.storeDetailId = storeDetailId;
    }
}
