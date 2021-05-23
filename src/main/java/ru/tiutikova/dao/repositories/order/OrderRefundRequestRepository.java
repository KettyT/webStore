package ru.tiutikova.dao.repositories.order;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.tiutikova.dao.entity.order.OrderRefundRequestEntity;

public interface OrderRefundRequestRepository extends JpaRepository<OrderRefundRequestEntity, Long> {



}
