package ru.tiutikova.dao.repositories.cart;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ru.tiutikova.dao.entity.cart.VCartInfoUserEntity;

import java.util.List;

public interface VCartInfoUserRepository extends CrudRepository<VCartInfoUserEntity, Long> {

    @Query(value = "select * from v_cart_info_user where user_id = ? ", nativeQuery = true)
    List<VCartInfoUserEntity> getByUserId(int userId);

    VCartInfoUserEntity getByUserIdAndDetailId(int userId, int detailId);

}
