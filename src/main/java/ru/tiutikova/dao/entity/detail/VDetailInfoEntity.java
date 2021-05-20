package ru.tiutikova.dao.entity.detail;

import javax.persistence.*;

@Entity
@Table(name = "v_detail_info", schema = "auto_market", catalog = "")
public class VDetailInfoEntity {
    @Id
    private int id;
    private int price;
    private int quantity;
    private Integer detailGroupListId;
    private String detailGroupListTextTitle;
    private String article;
    private String producerDetailCode;
    private int amountInPackage;
    private int detailId;
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
    @Column(name = "detail_group_list_id")
    public Integer getDetailGroupListId() {
        return detailGroupListId;
    }

    public void setDetailGroupListId(Integer detailGroupListId) {
        this.detailGroupListId = detailGroupListId;
    }

    public String getDetailGroupListTextTitle() {
        return detailGroupListTextTitle;
    }

    @Basic
    @Column(name = "detail_group_list_name")
    public void setDetailGroupListTextTitle(String detailGroupListTextTitle) {
        this.detailGroupListTextTitle = detailGroupListTextTitle;
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

    @Column(name = "detail_id")
    public int getDetailId() {
        return detailId;
    }

    public void setDetailId(int detailId) {
        this.detailId = detailId;
    }

    @Column(name = "produser_details_code")
    public String getProducerDetailCode() {
        return producerDetailCode;
    }

    public void setProducerDetailCode(String producerDetailCode) {
        this.producerDetailCode = producerDetailCode;
    }
}
