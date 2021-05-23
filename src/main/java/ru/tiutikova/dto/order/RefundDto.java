package ru.tiutikova.dto.order;

import java.util.List;

public class RefundDto {

    private String refundReason;

    private List<OrderDetailDto> refundedOrderList;

    public String getRefundReason() {
        return refundReason;
    }

    public void setRefundReason(String refundReason) {
        this.refundReason = refundReason;
    }

    public List<OrderDetailDto> getRefundedOrderList() {
        return refundedOrderList;
    }

    public void setRefundedOrderList(List<OrderDetailDto> refundedOrderList) {
        this.refundedOrderList = refundedOrderList;
    }
}
