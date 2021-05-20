package ru.tiutikova.dao.repositories.order;

import org.springframework.data.repository.CrudRepository;
import ru.tiutikova.dao.entity.order.VOrderDetailEntity;

import java.util.List;

public interface VOrderDetailRepository extends CrudRepository<VOrderDetailEntity, Long> {


    List<VOrderDetailEntity> getAllByOrderIdOrderById(int orderId);


}
