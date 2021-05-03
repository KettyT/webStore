package ru.tiutikova.dao.entity.cart;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "cart", schema = "auto_market", catalog = "")
public class CartEntity {

    private int id;

    private int sessionId;

    private BigDecimal summ;

    private int userId;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "session_id")
    public int getSessionId() {
        return sessionId;
    }

    public void setSessionId(int sessionId) {
        this.sessionId = sessionId;
    }

    @Column(name = "sum")
    public BigDecimal getSumm() {
        return summ;
    }

    public void setSumm(BigDecimal summ) {
        this.summ = summ;
    }

    @Column(name = "user_id")
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
