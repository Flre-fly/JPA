package jpaStudy.ex;

import jpaStudy.ex.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MyRepository extends JpaRepository<Member, Long> {
}
