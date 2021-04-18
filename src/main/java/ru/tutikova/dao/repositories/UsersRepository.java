package ru.tutikova.dao.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.tutikova.dao.entity.auth.UsersEntity;

public interface UsersRepository extends CrudRepository<UsersEntity, Long> {

    UsersEntity getUserByEmail(String email);



}
