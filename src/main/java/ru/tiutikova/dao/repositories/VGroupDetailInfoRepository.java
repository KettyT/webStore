package ru.tiutikova.dao.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.tiutikova.dao.entity.VGroupDetailInfoEntity;

import java.util.List;

public interface VGroupDetailInfoRepository extends CrudRepository<VGroupDetailInfoEntity, Long> {

    List<VGroupDetailInfoEntity> getAllByDetailGroupId(int id);

}
