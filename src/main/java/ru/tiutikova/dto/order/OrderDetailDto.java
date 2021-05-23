package ru.tiutikova.dto.order;

import ru.tiutikova.dao.entity.order.VOrderDetailEntity;

import java.math.BigDecimal;

public class OrderDetailDto {

    private Integer id;
    private int orderId;
    private BigDecimal price;
    private int quantity;
    private int storedDetailId;
    private int refundedQuantity;
    private String name;

    public OrderDetailDto() {
    }

    public OrderDetailDto(VOrderDetailEntity entity) {
        this.id = entity.getId();
        this.orderId = entity.getOrderId();
        this.price = entity.getPrice();
        this.quantity = entity.getQuantity();
        this.storedDetailId = entity.getStoredDetailId();
        this.refundedQuantity = entity.getRefundedQuantity();
        this.name = entity.getName();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getStoredDetailId() {
        return storedDetailId;
    }

    public void setStoredDetailId(int storedDetailId) {
        this.storedDetailId = storedDetailId;
    }

    public int getRefundedQuantity() {
        return refundedQuantity;
    }

    public void setRefundedQuantity(int refundedQuantity) {
        this.refundedQuantity = refundedQuantity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
