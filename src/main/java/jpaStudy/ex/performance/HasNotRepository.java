package jpaStudy.ex.performance;

import org.springframework.data.jpa.repository.JpaRepository;

public interface HasNotRepository extends JpaRepository<HasNotAnnotationEntity,Long> {
}
