package ru.tiutikova.dao.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.tiutikova.dao.entity.auth.SessionsEntity;

public interface SessionRepository extends JpaRepository<SessionsEntity, Long> {

    SessionsEntity getSessionsEntityBySessionCode(String sessionCode);

}
