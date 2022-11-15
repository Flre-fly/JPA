package jpaStudy.ex.repository.spring;

import jpaStudy.ex.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long>, MemberRepositoryCustom {
    List<Member> findByUsername(String userName);
}
