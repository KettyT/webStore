package ru.tiutikova.dao.repositories.cart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;

@Repository
public class CartNativeDao {

    private EntityManager entityManager;

    @Autowired
    public CartNativeDao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void moveStoredDetails (int cartFromId, int cartToId) {
        Query query = entityManager.createNativeQuery("update cart_details set cart_id = :toCartId where cart_id = :fromCartId");

        query.setParameter("toCartId", cartFromId);
        query.setParameter("fromCartId", cartToId);

        query.executeUpdate();
    }

}
