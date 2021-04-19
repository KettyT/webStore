package ru.tiutikova.dao.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.tiutikova.dao.entity.DetailGroupEntity;

import java.util.List;

@Repository
public interface DetailGroupRepository extends CrudRepository<DetailGroupEntity, Long> {

//    @Query("SELECT u FROM User u WHERE u.status = 1")
    @Query(
            value = "with recursive t1 as (\n" +
                    "    select     id, name, code, parent_id, 0 as level\n" +
                    "    from       details_groups dg\n" +
                    "    where      parent_id is null\n" +
                    "    union all\n" +
                    "    select     dg2.id, dg2.name, dg2.code, dg2.parent_id, (t1.level + 1) as level\n" +
                    "    from       details_groups dg2\n" +
                    "                   inner join t1\n" +
                    "                      on dg2.parent_id = t1.id\n" +
                    ")\n" +
                    "select * from t1",
            nativeQuery = true)
    List<DetailGroupEntity> getDetailGroupListTree();

}
