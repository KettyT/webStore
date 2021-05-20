package ru.tiutikova.dao.repositories.detail;

import org.springframework.data.repository.CrudRepository;
import ru.tiutikova.dao.entity.detail.DetailImagePositionEntity;

import java.util.List;

public interface DetailImagePositionRepository extends CrudRepository<DetailImagePositionEntity, Long> {

    List<DetailImagePositionEntity> getAllByDetailGroupListIdOrderById(int detailGroupListId);


}
