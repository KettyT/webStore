package ru.tiutikova.dao.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ru.tiutikova.dao.entity.auth.UsersEntity;

public interface UsersRepository extends CrudRepository<UsersEntity, Long> {

    UsersEntity getUserByEmail(String email);

    @Query(
            value = "select u.* from sessions s \n" +
                    "    join users u on u.id = s.user_id \n" +
                    "where s.session_code = ?;",
            nativeQuery = true)
    UsersEntity getBySessionCode(String sessionCode);

}
