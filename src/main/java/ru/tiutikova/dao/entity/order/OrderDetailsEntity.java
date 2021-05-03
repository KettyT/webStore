package ru.tiutikova.dao.entity.order;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "order_details", schema = "auto_market", catalog = "")
public class OrderDetailsEntity {
    private Integer id;
    private int orderId;
    private BigDecimal price;
    private int quantity;
    private int storedDetailId;
    private int refundedQuantity;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "order_id")
    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
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
    @Column(name = "quantity")
    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Basic
    @Column(name = "refunded_quantity")
    public int getRefundedQuantity() {
        return refundedQuantity;
    }

    public void setRefundedQuantity(int refundedQuantity) {
        this.refundedQuantity = refundedQuantity;
    }

    @Column(name = "stored_detail_id")
    public int getStoredDetailId() {
        return storedDetailId;
    }

    public void setStoredDetailId(int storedDetailId) {
        this.storedDetailId = storedDetailId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderDetailsEntity that = (OrderDetailsEntity) o;
        return quantity == that.quantity &&
                refundedQuantity == that.refundedQuantity &&
                Objects.equals(id, that.id) &&
                Objects.equals(price, that.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, price, quantity, refundedQuantity);
    }
}
