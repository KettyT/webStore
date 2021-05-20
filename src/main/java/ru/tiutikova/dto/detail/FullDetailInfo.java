package ru.tiutikova.dto.detail;

import ru.tiutikova.dao.entity.detail.DetailsEntity;

import java.util.ArrayList;
import java.util.List;

public class FullDetailInfo extends DetailDto {

    private List<VDetailInfoDto> detailInfoDtoList = new ArrayList<>();

    private List<VDetailInfoDto> analogInfoDtoList = new ArrayList<>();

    public FullDetailInfo(DetailsEntity entity) {
        super(entity);
    }

    public List<VDetailInfoDto> getDetailInfoDtoList() {
        return detailInfoDtoList;
    }

    public void setDetailInfoDtoList(List<VDetailInfoDto> detailInfoDtoList) {
        this.detailInfoDtoList = detailInfoDtoList;
    }

    public List<VDetailInfoDto> getAnalogInfoDtoList() {
        return analogInfoDtoList;
    }

    public void setAnalogInfoDtoList(List<VDetailInfoDto> analogInfoDtoList) {
        this.analogInfoDtoList = analogInfoDtoList;
    }
}
