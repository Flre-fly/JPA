package jpaStudy.ex.repository;

import jpaStudy.ex.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Users, Long> {
}
