package ru.tutikova.dao.repositories.cart;

import org.springframework.data.repository.CrudRepository;
import ru.tutikova.dao.entity.cart.CartEntity;

public interface CartRepository extends CrudRepository<CartEntity, Long> {

    CartEntity getBySessionId(int sessionId);

    @Override
    <S extends CartEntity> S save(S s);
}
