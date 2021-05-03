package ru.tiutikova.dao.repositories.order;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.tiutikova.dao.entity.order.OrdersEntity;

public interface OrderRepository extends JpaRepository<OrdersEntity, Long> {



}
