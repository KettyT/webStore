package ru.tiutikova.dao.repositories.cart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.tiutikova.UserException;
import ru.tiutikova.dao.entity.cart.VCartInfoUserEntity;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CartNativeDao {

    private EntityManager entityManager;

    private DataSource dataSource;

    @Autowired
    public CartNativeDao(EntityManager entityManager, DataSource dataSource) {
        this.entityManager = entityManager;
        this.dataSource = dataSource;
    }

    public void moveStoredDetails (int cartFromId, int cartToId) {
        Query query = entityManager.createNativeQuery("update cart_details set cart_id = :toCartId where cart_id = :fromCartId");

        query.setParameter("toCartId", cartToId);
        query.setParameter("fromCartId", cartFromId);

        query.executeUpdate();
    }

    public List<VCartInfoUserEntity> getCartInfoByUserId (int userId) {

        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement("select * from v_cart_info_user where user_id = ?")) {

            ps.setLong(1, userId);

            ResultSet rs = ps.executeQuery();

            List<VCartInfoUserEntity> result = new ArrayList<>();

            while (rs.next()) {
                VCartInfoUserEntity entity = new VCartInfoUserEntity();

                entity.setAmountInPackage(rs.getInt("amount_in_package"));
                entity.setCartId(rs.getInt("cart_id"));
                entity.setDetailId(rs.getInt("detail_id"));
                entity.setPrice(BigDecimal.valueOf(rs.getDouble("price")));
                entity.setItemName(rs.getString("item_name"));
                entity.setId(rs.getInt("id"));
                entity.setQuantity(rs.getInt("quantity"));
                entity.setUserId(rs.getInt("user_id"));
                entity.setSessionId(rs.getInt("session_id"));

                result.add(entity);
            }

            return result;

        } catch (Exception ex) {
            ex.printStackTrace();
            throw new UserException("getCartInfoByUserId ");
        }

        /*Query query = entityManager.createNativeQuery("select * from v_cart_info_user where user_id = :userId", VCartInfoUserEntity.class);

        query.setParameter("userId", userId);

        return (List<VCartInfoUserEntity>)query.getResultList();*/
    }

}
