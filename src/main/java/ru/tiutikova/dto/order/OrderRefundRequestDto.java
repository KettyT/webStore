package ru.tiutikova.dto.order;

import ru.tiutikova.dao.entity.order.OrderRefundRequestEntity;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class OrderRefundRequestDto {

    private Integer id;
    private String text;
    private Timestamp dateCreate;
    private Timestamp dateClosed;
    private String status;
    private int quantity;
    private int orderDetailId;
    private int userId;

    private String orderDetailName;

    private BigDecimal orderDetailPrice;

    public OrderRefundRequestDto(OrderRefundRequestEntity entity) {
        this.id = entity.getId();
        this.text = entity.getText();
        this.dateCreate = entity.getDateCreate();
        this.dateClosed = entity.getDateClosed();
        this.status = entity.getStatus();
        this.quantity = entity.getQuantity();
        this.orderDetailId = entity.getOrderDetailId();
        this.userId = entity.getUserId();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Timestamp getDateCreate() {
        return dateCreate;
    }

    public void setDateCreate(Timestamp dateCreate) {
        this.dateCreate = dateCreate;
    }

    public Timestamp getDateClosed() {
        return dateClosed;
    }

    public void setDateClosed(Timestamp dateClosed) {
        this.dateClosed = dateClosed;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getOrderDetailId() {
        return orderDetailId;
    }

    public void setOrderDetailId(int orderDetailId) {
        this.orderDetailId = orderDetailId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getOrderDetailName() {
        return orderDetailName;
    }

    public void setOrderDetailName(String orderDetailName) {
        this.orderDetailName = orderDetailName;
    }

    public BigDecimal getOrderDetailPrice() {
        return orderDetailPrice;
    }

    public void setOrderDetailPrice(BigDecimal orderDetailPrice) {
        this.orderDetailPrice = orderDetailPrice;
    }
}
