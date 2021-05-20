package ru.tiutikova.dao.entity.detail;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "detail_group_list", schema = "auto_market", catalog = "")
public class DetailGroupListEntity {
    private int id;
    private String url;
    private String textTitle;
    private Integer number;

    private Integer detailGroupId;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "details_group_id")
    public Integer getDetailGroupId() {
        return detailGroupId;
    }

    public void setDetailGroupId(Integer detailGroupId) {
        this.detailGroupId = detailGroupId;
    }

    @Basic
    @Column(name = "url")
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Basic
    @Column(name = "text_title")
    public String getTextTitle() {
        return textTitle;
    }

    public void setTextTitle(String textTitle) {
        this.textTitle = textTitle;
    }

    @Basic
    @Column(name = "number")
    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DetailGroupListEntity that = (DetailGroupListEntity) o;
        return id == that.id &&
                Objects.equals(url, that.url) &&
                Objects.equals(textTitle, that.textTitle) &&
                Objects.equals(number, that.number);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, url, textTitle, number);
    }
}
