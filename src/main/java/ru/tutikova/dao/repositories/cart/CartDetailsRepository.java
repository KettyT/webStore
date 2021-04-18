package ru.tutikova.dao.repositories.cart;

import org.springframework.data.repository.CrudRepository;
import ru.tutikova.dao.entity.cart.CartDetailsEntity;

public interface CartDetailsRepository extends CrudRepository<CartDetailsEntity, Long> {

    CartDetailsEntity getById(int id);

    @Override
    <S extends CartDetailsEntity> S save(S s);
}
