package ru.tiutikova.dto.detail;

import ru.tiutikova.dao.entity.detail.VDetailInfoEntity;

public class VDetailInfoDto {

    private int id;
    private int price;
    private int quantity;
    private Integer detailGroupListId;
    private String detailGroupListTextTitle;
    private String article;
    private int amountInPackage;
    private String detailName;

    private String producerName;

    private String countryName;

    public VDetailInfoDto(VDetailInfoEntity entity) {
        this.id = entity.getId();
        this.detailGroupListId = entity.getDetailGroupListId();
        this.detailGroupListTextTitle = entity.getDetailGroupListTextTitle();
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

    public Integer getDetailGroupListId() {
        return detailGroupListId;
    }

    public void setDetailGroupListId(Integer detailGroupListId) {
        this.detailGroupListId = detailGroupListId;
    }

    public String getDetailGroupListTextTitle() {
        return detailGroupListTextTitle;
    }

    public void setDetailGroupListTextTitle(String detailGroupListTextTitle) {
        this.detailGroupListTextTitle = detailGroupListTextTitle;
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

    public String getProducerName() {
        return producerName;
    }

    public void setProducerName(String producerName) {
        this.producerName = producerName;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }
}
