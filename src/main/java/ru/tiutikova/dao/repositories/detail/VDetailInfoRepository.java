package ru.tiutikova.dao.repositories.detail;

import org.springframework.data.repository.CrudRepository;
import ru.tiutikova.dao.entity.detail.VDetailInfoEntity;

import java.util.List;

public interface VDetailInfoRepository extends CrudRepository<VDetailInfoEntity, Long> {

    VDetailInfoEntity getById(int id);

    List<VDetailInfoEntity> getAllByProducerDetailCodeOrderById(String producerDetailCode);
}
