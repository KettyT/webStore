package ru.tiutikova.dto;

import ru.tiutikova.dao.entity.VGroupDetailInfoEntity;

public class VGroupDetailInfoDto {

    private String  id;
    private int detailGroupId;
    private String name;
    private String code;
    private int detailId;
    private String detailName;

    public VGroupDetailInfoDto(VGroupDetailInfoEntity entity) {
        this.id = entity.getId();
        this.detailGroupId = entity.getDetailGroupId();
        this.name = entity.getName();
        this.code = entity.getCode();
        this.detailId = entity.getDetailId();
        this.detailName = entity.getDetailName();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getDetailGroupId() {
        return detailGroupId;
    }

    public void setDetailGroupId(int detailGroupId) {
        this.detailGroupId = detailGroupId;
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

    public int getDetailId() {
        return detailId;
    }

    public void setDetailId(int detailId) {
        this.detailId = detailId;
    }

    public String getDetailName() {
        return detailName;
    }

    public void setDetailName(String detailName) {
        this.detailName = detailName;
    }
}
