package ru.tiutikova.dto;

import ru.tiutikova.dao.entity.DetailGroupEntity;

import java.util.ArrayList;
import java.util.List;

public class DetailGroupDto {
    private int id;
    private String name;
    private String code;
    private Integer parentId;
    private Integer level;

    List<DetailGroupDto> detailGroupDtoList = new ArrayList<>();

    public DetailGroupDto(DetailGroupEntity entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.code = entity.getCode();
        this.parentId = entity.getParentId();
        this.level = entity.getLevel();
    }

    public void addChild (DetailGroupDto child) {
        detailGroupDtoList.add(child);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public List<DetailGroupDto> getDetailGroupDtoList() {
        return detailGroupDtoList;
    }

    public void setDetailGroupDtoList(List<DetailGroupDto> detailGroupDtoList) {
        this.detailGroupDtoList = detailGroupDtoList;
    }
}
