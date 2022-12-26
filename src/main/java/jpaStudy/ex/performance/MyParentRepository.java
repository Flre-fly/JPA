package jpaStudy.ex.performance;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MyParentRepository extends JpaRepository<MyParent, Long> {
    @Query(value = "select p from MyParent p left outer join HasAnnotationEntity has on p.id = has.myParent.id where p.id = :id")
    Optional<MyParent> findJoinHas(@Param(value = "id") Long id);
    @Query(value = "select p from MyParent p left outer join HasNotAnnotationEntity has_not on p.id = has_not.parentId where p.id = :id")
    Optional<MyParent> findJoinNot(@Param(value = "id") Long id);


}