package ru.tiutikova.dto.detail;

import ru.tiutikova.dao.entity.detail.DetailGroupListEntity;

import java.util.ArrayList;
import java.util.List;

public class FullDetailGroupListDto extends DetailGroupListDto {

    private List<DetailImagePositionDto> detailImagePositionDtoList = new ArrayList<>();

    public FullDetailGroupListDto(DetailGroupListEntity entity) {
        super(entity);
    }

    public List<DetailImagePositionDto> getDetailImagePositionDtoList() {
        return detailImagePositionDtoList;
    }

    public void setDetailImagePositionDtoList(List<DetailImagePositionDto> detailImagePositionDtoList) {
        this.detailImagePositionDtoList = detailImagePositionDtoList;
    }
}
