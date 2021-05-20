package ru.tiutikova.dto.detail;

import ru.tiutikova.dao.entity.detail.DetailImagePositionEntity;

public class DetailImagePositionDto {

    private int id;
    private double x;
    private double y;
    private double width;
    private double height;
    private Integer detailId;
    private Integer detailGroupListId;

    public DetailImagePositionDto(DetailImagePositionEntity entity) {
        this.id = entity.getId();
        this.x = entity.getX();
        this.y = entity.getY();
        this.width = entity.getWidth();
        this.height = entity.getHeight();
        this.detailId = entity.getDetailId();
        this.detailGroupListId = entity.getDetailGroupListId();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Integer getDetailId() {
        return detailId;
    }

    public void setDetailId(Integer detailId) {
        this.detailId = detailId;
    }

    public Integer getDetailGroupListId() {
        return detailGroupListId;
    }

    public void setDetailGroupListId(Integer detailGroupListId) {
        this.detailGroupListId = detailGroupListId;
    }
}
