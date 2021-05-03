package ru.tiutikova.dao.repositories.detail;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.tiutikova.dao.entity.detail.StoredDetailsEntity;

public interface StoredDetailsRepository extends JpaRepository<StoredDetailsEntity, Long> {

}
