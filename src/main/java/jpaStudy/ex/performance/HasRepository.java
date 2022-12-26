package jpaStudy.ex.performance;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HasRepository extends JpaRepository<HasAnnotationEntity, Long> {

    List<HasAnnotationEntity> findByMyParentId(Long id);
}
