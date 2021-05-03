package ru.tiutikova.dao.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import ru.tiutikova.dao.entity.auth.SessionsEntity;

public interface SessionRepository extends JpaRepository<SessionsEntity, Long> {

    SessionsEntity getSessionsEntityBySessionCode(String sessionCode);

    @Modifying
    @Query(value = "update sessions set user_id = ? where session_code = ?", nativeQuery = true)
    int updateUserId (int userId, String sessionCode);

    SessionsEntity getBySessionCode(String sessionCode);
}
