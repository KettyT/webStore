package ru.tiutikova.dao.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "details", schema = "auto_market", catalog = "")
public class DetailsEntity {
    private int id;
    private int groupId;
    private String name;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "group_id")
    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DetailsEntity that = (DetailsEntity) o;
        return id == that.id &&
                groupId == that.groupId &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, groupId, name);
    }
}
