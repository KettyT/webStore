package ru.tiutikova.dao.repositories.order;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.tiutikova.dao.entity.order.OrderDetailsEntity;

import java.util.List;

public interface OrderDetailsRepository extends JpaRepository<OrderDetailsEntity, Long> {

    List<OrderDetailsEntity> getAllByOrderIdOrderById(int orderId);

}
