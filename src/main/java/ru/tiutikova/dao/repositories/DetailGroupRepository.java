package ru.tiutikova.dao.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.tiutikova.dao.entity.detail.DetailGroupEntity;

import java.util.List;

@Repository
public interface DetailGroupRepository extends CrudRepository<DetailGroupEntity, Long> {

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


    @Query(
            value = "with recursive t1 as (\n" +
                    "    select     id, name, code, parent_id, 0 as level\n" +
                    "    from       details_groups dg\n" +
                    "    where      id in (select dg.id from details d\n" +
                    "        join detail_group_list dgl on dgl.id = d.detail_group_list_id\n" +
                    "        join details_groups dg on dgl.details_group_id = dg.id\n" +
                    "        where d.id in :detailIdList \n" +
                    "    )\n" +
                    "    union all\n" +
                    "    select     dg2.id, dg2.name, dg2.code, dg2.parent_id, (t1.level + 1) as level\n" +
                    "    from       details_groups dg2\n" +
                    "                   inner join t1\n" +
                    "                              on dg2.id = t1.parent_id\n" +
                    ")\n" +
                    "select * from t1;",
            nativeQuery = true)
    List<DetailGroupEntity> getDetailGroupListTreeByDetailIds(@Param(("detailIdList") ) List<Integer> detailIdList);
}


