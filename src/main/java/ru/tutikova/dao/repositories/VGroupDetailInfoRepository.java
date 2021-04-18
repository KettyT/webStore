package ru.tutikova.dao.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.tutikova.dao.entity.DetailGroupEntity;
import ru.tutikova.dao.entity.VGroupDetailInfoEntity;

import java.util.List;

public interface VGroupDetailInfoRepository extends CrudRepository<VGroupDetailInfoEntity, Long> {

    List<VGroupDetailInfoEntity> getAllByDetailGroupId(int id);

}
