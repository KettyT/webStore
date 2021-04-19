package ru.tiutikova.dto;

import ru.tiutikova.dao.entity.VDetailInfoEntity;

public class VDetailInfoDto {

    private int id;
    private int price;
    private int quantity;
    private Integer detailGroupId;
    private String detailGroupName;
    private String article;
    private int amountInPackage;
    private String detailName;

    private String producerName;

    private String countryName;

    public VDetailInfoDto(VDetailInfoEntity entity) {
        this.id = entity.getId();
        this.detailGroupId = entity.getDetailGroupId();
        this.detailGroupName = entity.getDetailGroupName();
        this.article = entity.getArticle();
        this.amountInPackage = entity.getAmountInPackage();
        this.price = entity.getPrice();
        this.quantity = entity.getQuantity();
        this.detailName = entity.getDetailName();
        this.producerName = entity.getProducerName();
        this.countryName = entity.getCountryName();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Integer getDetailGroupId() {
        return detailGroupId;
    }

    public void setDetailGroupId(Integer detailGroupId) {
        this.detailGroupId = detailGroupId;
    }

    public String getDetailGroupName() {
        return detailGroupName;
    }

    public void setDetailGroupName(String detailGroupName) {
        this.detailGroupName = detailGroupName;
    }

    public String getArticle() {
        return article;
    }

    public void setArticle(String article) {
        this.article = article;
    }

    public int getAmountInPackage() {
        return amountInPackage;
    }

    public void setAmountInPackage(int amountInPackage) {
        this.amountInPackage = amountInPackage;
    }

    public String getDetailName() {
        return detailName;
    }

    public void setDetailName(String detailName) {
        this.detailName = detailName;
    }
}
