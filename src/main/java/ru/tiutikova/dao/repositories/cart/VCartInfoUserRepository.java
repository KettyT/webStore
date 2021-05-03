package ru.tiutikova.dao.repositories.cart;

import org.springframework.data.repository.CrudRepository;
import ru.tiutikova.dao.entity.cart.VCartInfoUserEntity;

import java.util.List;

public interface VCartInfoUserRepository extends CrudRepository<VCartInfoUserEntity, Long> {



    List<VCartInfoUserEntity> getByUserId(int userId);

    VCartInfoUserEntity getByUserIdAndDetailId(int userId, int detailId);

}
