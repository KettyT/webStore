package ru.tiutikova.dao.repositories.detail;

import org.springframework.data.repository.CrudRepository;
import ru.tiutikova.dao.entity.detail.DetailsEntity;

import java.util.List;

public interface DetailsRepository extends CrudRepository<DetailsEntity, Long> {

    DetailsEntity getById(int id);

    List<DetailsEntity> getAllByDetailGroupListIdInOrderById(List<Integer> detailGroupListIds);

    List<DetailsEntity> getAllByNameIsLike(String query);

}
