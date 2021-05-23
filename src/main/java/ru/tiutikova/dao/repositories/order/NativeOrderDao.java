package ru.tiutikova.dao.repositories.order;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.Map;

@Repository
public class NativeOrderDao {

    private EntityManager entityManager;

    @Autowired
    public NativeOrderDao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void updateStoreDetailsQuantity (Map<Integer, Integer> quantityMap) {

        Query query = entityManager.createNativeQuery("update stored_details set quantity = :quantity where id = :id");

        for (Integer storedDetailId : quantityMap.keySet()) {
            query.setParameter("quantity", quantityMap.get(storedDetailId));
            query.setParameter("id", storedDetailId);

            query.executeUpdate();
        }

    }

}
