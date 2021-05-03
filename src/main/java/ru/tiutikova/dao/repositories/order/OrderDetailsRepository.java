package ru.tiutikova.dao.repositories.order;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.tiutikova.dao.entity.order.OrderDetailsEntity;

public interface OrderDetailsRepository extends JpaRepository<OrderDetailsEntity, Long> {
}
