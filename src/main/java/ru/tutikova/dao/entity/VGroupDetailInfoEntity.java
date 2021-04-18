package ru.tutikova.dao.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "v_group_detail_info", schema = "auto_market", catalog = "")
public class VGroupDetailInfoEntity {
    @Id
    private String id;

    private int detailGroupId;

    private String name;
    private String code;
    private int detailId;
    private String detailName;

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "code")
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Basic
    @Column(name = "detail_id")
    public int getDetailId() {
        return detailId;
    }

    public void setDetailId(int detailId) {
        this.detailId = detailId;
    }

    @Basic
    @Column(name = "id")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Column(name = "detail_group_id")
    public int getDetailGroupId() {
        return detailGroupId;
    }

    public void setDetailGroupId(int detailGroupId) {
        this.detailGroupId = detailGroupId;
    }

    @Basic
    @Column(name = "detail_name")
    public String getDetailName() {
        return detailName;
    }

    public void setDetailName(String detailName) {
        this.detailName = detailName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VGroupDetailInfoEntity that = (VGroupDetailInfoEntity) o;
        return id == that.id &&
                detailId == that.detailId &&
                Objects.equals(name, that.name) &&
                Objects.equals(code, that.code) &&
                Objects.equals(detailName, that.detailName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, code, detailId, detailName);
    }
}
