package ru.tutikova.dao.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.tutikova.dao.entity.VDetailInfoEntity;

public interface VDetailInfoRepository extends CrudRepository<VDetailInfoEntity, Long> {

    VDetailInfoEntity getById(int id);

}
