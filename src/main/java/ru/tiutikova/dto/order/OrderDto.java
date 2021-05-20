package ru.tiutikova.dto.order;

import com.fasterxml.jackson.annotation.JsonIgnore;
import ru.tiutikova.dao.entity.order.OrdersEntity;
import ru.tiutikova.utils.DateTimeUtils;

import java.time.LocalDateTime;
import java.time.ZoneId;

public class OrderDto {

    private int id;

    @JsonIgnore
    private int userId;
    private int number;
    private String dateDelivery;
    private String dateCreate;
    private String status;

    public OrderDto() {
    }

    public OrderDto(OrdersEntity entity) {
        this.id = entity.getId();
        this.userId = entity.getUserId();
        this.number = entity.getNumber();
        this.dateDelivery = DateTimeUtils.STANDART_DATE_FORMATTER.format(LocalDateTime.ofInstant(entity.getDateDelivery().toInstant(), ZoneId.systemDefault()));
        this.dateCreate = DateTimeUtils.STANDART_DATE_FORMATTER.format(LocalDateTime.ofInstant(entity.getDateCreate().toInstant(), ZoneId.systemDefault()));
        this.status = entity.getStatus();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getDateDelivery() {
        return dateDelivery;
    }

    public void setDateDelivery(String dateDelivery) {
        this.dateDelivery = dateDelivery;
    }

    public String getDateCreate() {
        return dateCreate;
    }

    public void setDateCreate(String dateCreate) {
        this.dateCreate = dateCreate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
