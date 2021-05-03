package ru.tiutikova.dao.entity.auth;

import javax.persistence.*;

@Entity
@Table(name = "sessions", schema = "auto_market", catalog = "")
public class SessionsEntity {
    private int id;
    private String sessionCode;

//    private UsersEntity usersEntity;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer userId;

    @Basic
    @Column(name = "session_code")
    public String getSessionCode() {
        return sessionCode;
    }

    public void setSessionCode(String sessionCode) {
        this.sessionCode = sessionCode;
    }



    /*@ManyToOne
    @JoinColumn(name = "user_id")
    public UsersEntity getUsersEntity() {
        return usersEntity;
    }

    public void setUsersEntity(UsersEntity usersEntity) {
        this.usersEntity = usersEntity;
    }*/

    @Column(name = "user_id")
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
