package ru.tiutikova.dto.order;

import ru.tiutikova.dao.entity.order.OrdersEntity;

import java.util.ArrayList;
import java.util.List;

public class FullOrderInfoDto extends OrderDto {

    private List<OrderDetailDto> orderDetailDtoList = new ArrayList<>();

    public FullOrderInfoDto(OrdersEntity entity, List<OrderDetailDto> orderDetailDtoList) {
        super(entity);
        this.orderDetailDtoList = orderDetailDtoList;
    }

    public List<OrderDetailDto> getOrderDetailDtoList() {
        return orderDetailDtoList;
    }

    public void setOrderDetailDtoList(List<OrderDetailDto> orderDetailDtoList) {
        this.orderDetailDtoList = orderDetailDtoList;
    }
}
