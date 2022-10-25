package jpaStudy.ex;

import jpaStudy.ex.entity.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.repository.core.EntityInformation;
import org.springframework.test.annotation.Commit;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class ExApplicationTests {

	@Autowired
	private EntityManager em;
	@Autowired
	private MyRepository repository;
	@Test
	@Transactional
	void test() {
		Member member = Member.builder().name("김길동").build();
		em.persist(member);
		em.detach(member);
		System.out.println(em.contains(member));
		//준영속상태의 entity는 영속석컨텍스트에서 제거된다

	}
	@Test
	@Transactional
	public void Parent_Child(){
		Member member = Member.builder().name("김길동").build();
		em.persist(member);
		System.out.println("-=====");
		member.setName("이름바꿈");
		System.out.println("====");
		//commit을 하지 않으면 마지막 쿼리는 안보인다
	}


	@Test
	@Commit
	@Transactional
	public void updateQuery(){
		Member member = Member.builder().name("멤버1").build();
		Team team = new Team("team1");
		team.settingMember(member);
		em.persist(team);
		//update쿼리가 왜날아가는지 모르겠다
	}

	@Test
	@Transactional
	@Commit
	public void 일대일조인테이블(){
		Parent p = new Parent();
		Child c = new Child();
		p.setChild(c);
		em.persist(p);
		em.persist(c);

	}


}
