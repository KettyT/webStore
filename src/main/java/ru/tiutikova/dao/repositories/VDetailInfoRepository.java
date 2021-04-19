package ru.tiutikova.dao.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.tiutikova.dao.entity.VDetailInfoEntity;

public interface VDetailInfoRepository extends CrudRepository<VDetailInfoEntity, Long> {

    VDetailInfoEntity getById(int id);

}
