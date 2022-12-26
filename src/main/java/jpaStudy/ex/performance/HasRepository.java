package jpaStudy.ex.performance;

import org.springframework.data.jpa.repository.JpaRepository;

public interface HasRepository extends JpaRepository<HasAnnotationEntity, Long> {
}
