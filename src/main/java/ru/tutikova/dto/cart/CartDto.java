package ru.tutikova.dto.cart;

import ru.tutikova.dto.SimpleDto;

import java.math.BigDecimal;

public class CartDto extends SimpleDto {

    private int count;

    private BigDecimal totalSumm;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public BigDecimal getTotalSumm() {
        return totalSumm;
    }

    public void setTotalSumm(BigDecimal totalSumm) {
        this.totalSumm = totalSumm;
    }
}
