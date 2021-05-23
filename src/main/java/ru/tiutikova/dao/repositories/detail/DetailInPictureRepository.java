package ru.tiutikova.dao.repositories.detail;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ru.tiutikova.dao.entity.detail.DetailInPictureEntity;

import java.util.List;

public interface DetailInPictureRepository extends CrudRepository<DetailInPictureEntity, Long> {

    @Query(value = "select\n" +
            "    d.*,\n" +
            "    pd.code as producer_code,\n" +
            "    exists(select * from details d2 where d2.article_original = pd.article) as is_original\n" +
            " from details d\n" +
            "    join producer_details pd on pd.detail_id = d.id\n" +
            "where pd.code in (select code from producer_details where detail_id in :detailIdList ) " +
            " order by id", nativeQuery = true)
    List<DetailInPictureEntity> getAllDetailInPicture (@Param("detailIdList") List<Integer> detailIdList);

}
