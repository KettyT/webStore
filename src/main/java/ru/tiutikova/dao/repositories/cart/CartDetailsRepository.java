package ru.tiutikova.dao.repositories.cart;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ru.tiutikova.dao.entity.cart.CartDetailsEntity;

import java.util.List;

public interface CartDetailsRepository extends CrudRepository<CartDetailsEntity, Long> {

    CartDetailsEntity getById(int id);

    @Override
    <S extends CartDetailsEntity> S save(S s);

    @Query(nativeQuery = true, value = "select cd.* from users u \n" +
            "    join sessions s on u.id = s.user_id \n" +
            "    join cart c on (s.id = c.session_id or u.id = c.user_id) \n" +
            "    join cart_details cd on cd.cart_id = c.id \n" +
            "where u.id = 1")
    List<CartDetailsEntity> getCartDetailsByUserId(int userId);

    void deleteAllByCartId(int cartId);

}
