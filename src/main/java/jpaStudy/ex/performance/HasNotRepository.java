package jpaStudy.ex.performance;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface HasNotRepository extends JpaRepository<HasNotAnnotationEntity,Long> {

    @Query("select n from HasNotAnnotationEntity n where n.myParent.id = :id")
    List<HasNotAnnotationEntity> findByMyParentId(@Param(value = "id") Long id);
}
