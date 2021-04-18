package ru.tutikova.dao.repositories.cart;

import org.springframework.data.repository.CrudRepository;
import ru.tutikova.dao.entity.cart.VCartInfoEntity;

import java.util.List;

public interface VCartInfoRepository extends CrudRepository<VCartInfoEntity, Long> {


    List<VCartInfoEntity> getAllBySessionCode(String sessionId);

    VCartInfoEntity getBySessionCodeAndDetailId(String sessionCode, int detailId);
}
