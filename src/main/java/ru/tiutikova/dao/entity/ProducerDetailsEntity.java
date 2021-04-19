package ru.tiutikova.dao.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "producer_details", schema = "auto_market", catalog = "")
public class ProducerDetailsEntity {
    private int id;
    private int producerId;
    private int code;
    private String article;
    private int price;
    private int amountInPackage;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "producer_id")
    public int getProducerId() {
        return producerId;
    }

    public void setProducerId(int producerId) {
        this.producerId = producerId;
    }

    @Basic
    @Column(name = "code")
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
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
    @Column(name = "price")
    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Basic
    @Column(name = "amount_in_package")
    public int getAmountInPackage() {
        return amountInPackage;
    }

    public void setAmountInPackage(int amountInPackage) {
        this.amountInPackage = amountInPackage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProducerDetailsEntity that = (ProducerDetailsEntity) o;
        return id == that.id &&
                producerId == that.producerId &&
                code == that.code &&
                price == that.price &&
                amountInPackage == that.amountInPackage &&
                Objects.equals(article, that.article);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, producerId, code, article, price, amountInPackage);
    }
}
