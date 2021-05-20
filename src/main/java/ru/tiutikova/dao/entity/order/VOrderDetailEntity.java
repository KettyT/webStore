package ru.tiutikova.dao.entity.order;



import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "v_order_detail", schema = "auto_market", catalog = "")
public class VOrderDetailEntity {
    private int id;
    private int quantity;
    private BigDecimal price;
    private Integer orderId;
    private int refundedQuantity;
    private Integer storedDetailId;
    private String name;

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

    @Basic
    @Column(name = "price")
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Basic
    @Column(name = "order_id")
    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    @Basic
    @Column(name = "refunded_quantity")
    public int getRefundedQuantity() {
        return refundedQuantity;
    }

    public void setRefundedQuantity(int refundedQuantity) {
        this.refundedQuantity = refundedQuantity;
    }

    @Basic
    @Column(name = "stored_detail_id")
    public Integer getStoredDetailId() {
        return storedDetailId;
    }

    public void setStoredDetailId(Integer storedDetailId) {
        this.storedDetailId = storedDetailId;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VOrderDetailEntity that = (VOrderDetailEntity) o;
        return id == that.id &&
                quantity == that.quantity &&
                refundedQuantity == that.refundedQuantity &&
                Objects.equals(price, that.price) &&
                Objects.equals(orderId, that.orderId) &&
                Objects.equals(storedDetailId, that.storedDetailId) &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, quantity, price, orderId, refundedQuantity, storedDetailId, name);
    }
}
