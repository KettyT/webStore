package ru.tiutikova.dao.repositories.detail;

import org.springframework.data.repository.CrudRepository;
import ru.tiutikova.dao.entity.detail.DetailGroupListEntity;

import java.util.List;

public interface DetailGroupListRepository extends CrudRepository<DetailGroupListEntity, Long> {

    DetailGroupListEntity getById(int id);

    List<DetailGroupListEntity> getByDetailGroupIdOrderById(int detailGroupId);

}
