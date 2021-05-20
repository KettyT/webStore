package ru.tiutikova.dto.detail;

import ru.tiutikova.dao.entity.detail.DetailsEntity;

public class DetailDto {

    private int id;
    private Integer detailGroupListId;
    private String name;
    private String originalArticle;

    public DetailDto(DetailsEntity entity) {
        this.id = entity.getId();
        this.detailGroupListId = entity.getDetailGroupListId();
        this.name = entity.getName();
        this.originalArticle = entity.getOriginalArticle();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getDetailGroupListId() {
        return detailGroupListId;
    }

    public void setDetailGroupListId(Integer detailGroupListId) {
        this.detailGroupListId = detailGroupListId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOriginalArticle() {
        return originalArticle;
    }

    public void setOriginalArticle(String originalArticle) {
        this.originalArticle = originalArticle;
    }
}
