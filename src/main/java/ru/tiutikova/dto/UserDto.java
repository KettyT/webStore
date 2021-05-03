package ru.tiutikova.dto;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import ru.tiutikova.dao.entity.auth.UsersEntity;

import java.util.Collection;

public class UserDto implements Authentication {
    private int id;

    private String email;
    private String name;
    private String surname;
    private String password;
    private String sessionId;

    boolean authentificated = false;

    public UserDto() {
    }

    public UserDto(UsersEntity entity) {
        this.id = entity.getId();
        this.email = entity.getEmail();
        this.name = entity.getName();
        this.surname = entity.getSurname();
        this.password = entity.getPassword();
//        this.sessionId = sessionId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public Object getCredentials() {
        return password;
    }

    @Override
    public Object getDetails() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return null;
    }

    @Override
    public boolean isAuthenticated() {
        return true;
        //        return authentificated;
    }

    @Override
    public void setAuthenticated(boolean b) throws IllegalArgumentException {
        authentificated = b;
    }
}
