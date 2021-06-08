package ru.tiutikova.dao.repositories.order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.tiutikova.dao.entity.order.OrderRefundRequestEntity;

import java.util.List;

public interface OrderRefundRequestRepository extends JpaRepository<OrderRefundRequestEntity, Long> {

    @Query(value = "select * from order_refund_request where order_detail_id in :orderDetailList ", nativeQuery = true)
    List<OrderRefundRequestEntity> getAllRefundRequestByOrderDetailList(@Param("orderDetailList") List<Integer> orderDetailList);

}
