package ru.tiutikova.dto.order;

import ru.tiutikova.dao.entity.order.OrdersEntity;

import java.util.ArrayList;
import java.util.List;

public class FullOrderInfoDto extends OrderDto {

    private List<OrderDetailDto> orderDetailDtoList = new ArrayList<>();

    private  List<OrderRefundRequestDto> orderRefundRequestDtoList = new ArrayList<>();

    public FullOrderInfoDto(OrdersEntity entity, List<OrderDetailDto> orderDetailDtoList,
                            List<OrderRefundRequestDto> orderRefundRequestDtoList) {
        super(entity);
        this.orderDetailDtoList = orderDetailDtoList;
        this.orderRefundRequestDtoList = orderRefundRequestDtoList;
    }

    public List<OrderDetailDto> getOrderDetailDtoList() {
        return orderDetailDtoList;
    }

    public void setOrderDetailDtoList(List<OrderDetailDto> orderDetailDtoList) {
        this.orderDetailDtoList = orderDetailDtoList;
    }

    public List<OrderRefundRequestDto> getOrderRefundRequestDtoList() {
        return orderRefundRequestDtoList;
    }

    public void setOrderRefundRequestDtoList(List<OrderRefundRequestDto> orderRefundRequestDtoList) {
        this.orderRefundRequestDtoList = orderRefundRequestDtoList;
    }
}
