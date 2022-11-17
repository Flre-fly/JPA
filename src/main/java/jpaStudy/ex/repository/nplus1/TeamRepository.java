package jpaStudy.ex.repository.nplus1;

import jpaStudy.ex.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<Team, Long> {
}
