package ru.tiutikova.dto.detail;

import ru.tiutikova.dao.entity.detail.DetailGroupListEntity;

import java.util.ArrayList;
import java.util.List;

public class DetailGroupListDto {

    private int id;
    private String url;
    private String textTitle;
    private Integer number;
    private Integer detailGroupId;

    List<DetailDto> detailDtoList = new ArrayList<>();

    public DetailGroupListDto(DetailGroupListEntity entity) {
        this.id = entity.getId();
        this.url = entity.getUrl();
        this.textTitle = entity.getTextTitle();
        this.number = entity.getNumber();
        this.detailGroupId = entity.getDetailGroupId();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTextTitle() {
        return textTitle;
    }

    public void setTextTitle(String textTitle) {
        this.textTitle = textTitle;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Integer getDetailGroupId() {
        return detailGroupId;
    }

    public void setDetailGroupId(Integer detailGroupId) {
        this.detailGroupId = detailGroupId;
    }

    public List<DetailDto> getDetailDtoList() {
        return detailDtoList;
    }

    public void setDetailDtoList(List<DetailDto> detailDtoList) {
        this.detailDtoList = detailDtoList;
    }
}
