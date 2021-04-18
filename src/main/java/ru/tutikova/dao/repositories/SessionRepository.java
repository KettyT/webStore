package ru.tutikova.dao.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import ru.tutikova.dao.entity.auth.SessionsEntity;

public interface SessionRepository extends JpaRepository<SessionsEntity, Long> {

    SessionsEntity getSessionsEntityBySessionCode(String sessionCode);

}
