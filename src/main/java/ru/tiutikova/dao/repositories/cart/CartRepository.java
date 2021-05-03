package ru.tiutikova.dao.repositories.cart;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.tiutikova.dao.entity.cart.CartEntity;

public interface CartRepository extends JpaRepository<CartEntity, Long> {

    CartEntity getBySessionId(int sessionId);

    CartEntity getByUserId(int userId);

    @Override
    <S extends CartEntity> S save(S s);
}
