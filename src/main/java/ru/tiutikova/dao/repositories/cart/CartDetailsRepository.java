package ru.tiutikova.dao.repositories.cart;

import org.springframework.data.repository.CrudRepository;
import ru.tiutikova.dao.entity.cart.CartDetailsEntity;

public interface CartDetailsRepository extends CrudRepository<CartDetailsEntity, Long> {

    CartDetailsEntity getById(int id);

    @Override
    <S extends CartDetailsEntity> S save(S s);
}
