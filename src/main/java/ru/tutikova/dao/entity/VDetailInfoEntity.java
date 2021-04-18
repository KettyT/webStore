package ru.tutikova.dao.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "v_detail_info", schema = "auto_market", catalog = "")
public class VDetailInfoEntity {
    @Id
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

    @Basic
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "price")
    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Basic
    @Column(name = "quantity")
    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Basic
    @Column(name = "detail_group_id")
    public Integer getDetailGroupId() {
        return detailGroupId;
    }

    public void setDetailGroupId(Integer detailGroupId) {
        this.detailGroupId = detailGroupId;
    }

    @Basic
    @Column(name = "detail_group_name")
    public String getDetailGroupName() {
        return detailGroupName;
    }

    public void setDetailGroupName(String detailGroupName) {
        this.detailGroupName = detailGroupName;
    }

    @Basic
    @Column(name = "article")
    public String getArticle() {
        return article;
    }

    public void setArticle(String article) {
        this.article = article;
    }

    @Basic
    @Column(name = "amount_in_package")
    public int getAmountInPackage() {
        return amountInPackage;
    }

    public void setAmountInPackage(int amountInPackage) {
        this.amountInPackage = amountInPackage;
    }

    @Basic
    @Column(name = "detail_name")
    public String getDetailName() {
        return detailName;
    }

    public void setDetailName(String detailName) {
        this.detailName = detailName;
    }

    @Column(name = "producer_name")
    public String getProducerName() {
        return producerName;
    }

    public void setProducerName(String producerName) {
        this.producerName = producerName;
    }

    @Column(name = "country_name")
    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VDetailInfoEntity that = (VDetailInfoEntity) o;
        return id == that.id &&
                price == that.price &&
                quantity == that.quantity &&
                amountInPackage == that.amountInPackage &&
                Objects.equals(detailGroupId, that.detailGroupId) &&
                Objects.equals(detailGroupName, that.detailGroupName) &&
                Objects.equals(article, that.article) &&
                Objects.equals(detailName, that.detailName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, price, quantity, detailGroupId, detailGroupName, article, amountInPackage, detailName);
    }
}
