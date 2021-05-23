package ru.tiutikova.dao.entity.detail;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class DetailInPictureEntity {

    private int id;
    private Integer detailGroupListId;
    private String name;
    private String originalArticle;
    private String code;

    private Integer isOriginal;

    private String producerCode;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "detail_group_list_id")
    public Integer getDetailGroupListId() {
        return detailGroupListId;
    }

    public void setDetailGroupListId(Integer detailGroupListId) {
        this.detailGroupListId = detailGroupListId;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "article_original")
    public String getOriginalArticle() {
        return originalArticle;
    }

    public void setOriginalArticle(String originalArticle) {
        this.originalArticle = originalArticle;
    }

    @Column(name = "code")
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Column(name = "is_original")
    public Integer getIsOriginal() {
        return isOriginal;
    }

    public void setIsOriginal(Integer isOriginal) {
        this.isOriginal = isOriginal;
    }

    @Column(name = "producer_code")
    public String getProducerCode() {
        return producerCode;
    }

    public void setProducerCode(String producerCode) {
        this.producerCode = producerCode;
    }
}
