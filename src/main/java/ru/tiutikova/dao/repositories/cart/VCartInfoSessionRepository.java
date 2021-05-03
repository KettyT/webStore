package ru.tiutikova.dao.repositories.cart;

import org.springframework.data.repository.CrudRepository;
import ru.tiutikova.dao.entity.cart.VCartInfoSessionEntity;

import java.util.List;

public interface VCartInfoSessionRepository extends CrudRepository<VCartInfoSessionEntity, Long> {


    List<VCartInfoSessionEntity> getAllBySessionCode(String sessionId);

    VCartInfoSessionEntity getBySessionCodeAndDetailId(String sessionCode, int detailId);
}
