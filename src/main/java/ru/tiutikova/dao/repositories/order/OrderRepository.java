package ru.tiutikova.dao.repositories.order;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.tiutikova.dao.entity.order.OrdersEntity;

import java.util.List;

public interface OrderRepository extends JpaRepository<OrdersEntity, Long> {
    List<OrdersEntity> getAllByUserIdOrderByDateCreateDesc(int userId);

    OrdersEntity getByIdAndUserId(int id, int userId);
}
