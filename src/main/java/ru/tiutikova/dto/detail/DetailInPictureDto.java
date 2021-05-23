package ru.tiutikova.dto.detail;

import ru.tiutikova.dao.entity.detail.DetailsEntity;

import java.util.ArrayList;
import java.util.List;

public class DetailInPictureDto extends DetailDto {

    List<String> aliasList = new ArrayList<>();

    public DetailInPictureDto(DetailsEntity entity) {
        super(entity);
    }

    public List<String> getAliasList() {
        return aliasList;
    }

    public void setAliasList(List<String> aliasList) {
        this.aliasList = aliasList;
    }
}
