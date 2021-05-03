package ru.tiutikova.dto.cart;

import ru.tiutikova.dto.SimpleDto;

import java.math.BigDecimal;

public class CartDto extends SimpleDto {

    private int count;

    private BigDecimal totalSumm;

    private String name;

    private String surname;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }
}
