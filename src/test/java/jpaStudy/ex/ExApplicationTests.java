package jpaStudy.ex;

import jpaStudy.ex.entity.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.repository.core.EntityInformation;
import org.springframework.test.annotation.Commit;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
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
	@Commit
	public void TypeQuery를활용한쿼리(){
		TypedQuery<Member> query = em.createQuery("select m from Member m", Member.class);
		List<Member> list = query.getResultList();
		for (Member member: list) {
			System.out.println(member.getName());
		}
	}
	@Test
	@Transactional
	@Commit
	//error!
	public void 임베디드값조회(){

		List<Object[]> list = em.createQuery("select m.addressList from Member m").getResultList();
		for (Object[] o : list) {
			System.out.println(o);
		}
	}
	@Test
	@Transactional
	@Commit
	public void 스칼라값조회(){
		List<String> usernames = em.createQuery("SELECT m.name FROM Member m", String.class).getResultList();
		for (String str: usernames) {
			System.out.println(str);
		}
	}
	@Test
	@Transactional
	@Commit
	public void 스칼라값리스트조회(){
		List list = em.createQuery("SELECT m.name, m.id FROM Member m").getResultList();
		for (Object o: list) {
			System.out.println(o);
		}
	}

	@Test
	@Transactional
	@Commit
	public void new명령어사용하기() {
		List<MemberDto> list = em.createQuery("SELECT new jpaStudy.ex.entity.MemberDto(m.id, m.name) FROM Member m").getResultList();
		for (MemberDto dto : list) {
			System.out.println(dto);
		}
	}

	@Test
	@Transactional
	@Commit
	public void 페이징처리(){
		TypedQuery<Member> query = em.createQuery("SELECT m FROM Member m ORDER BY m.name", Member.class);
		query.setFirstResult(10);
		query.setMaxResults(20);//11-20번의 데이터 조회
		List<Member> members =query.getResultList();
		for (Member member: members) {
			System.out.println(member);
		}
	}
	@Test
	@Transactional
	public void 집합함수(){
		List<Long> list = em.createQuery("select SUM(m.id) from Member m", Long.class).getResultList();//이거 왜 list로 받아야되는거지
		for (Long x: list) {
			System.out.println(x);
		}
	}
	//그룹별 통계 데이터 중 평균나이가 10살 이상인 그룹을 조회한
	@Test
	@Transactional
	public void 예제1(){
		List<Double> list = em.createQuery("select avg(m.id) from Member m group by m.name having avg(m.id) > 10", Double.class).getResultList();
		for (Double x: list) {
			System.out.println(x);
		}
	}
	@Test
	@Transactional
	public void 조인(){
		String teamName = "teamA";
		String query = "select m from Member m inner join m.team t where t.name =:teamName";
		List<Member> members = em.createQuery(query, Member.class).setParameter("teamName", teamName).getResultList();
		//내부조인이라 Member로 받을 수 있음
	}
	@Test
	@Transactional
	@Commit
	public void init(){
		Team team1 = new Team();
		team1.setName("aa");
		Team team2 = new Team();
		team2.setName("bb");
		Member member1 = new Member();
		member1.setName("1");
		member1.setTeam(team1);
		Member member2 = new Member();
		member2.setName("2");
		member2.setTeam(team2);
		Member member3 = new Member();
		member3.setName("3");
		member3.setTeam(team2);
		em.persist(team1);
		em.persist(team2);
		em.persist(member1);
		em.persist(member2);
		em.persist(member3);
	}

	@Test
	@Transactional
	public void 팀멤버조인(){
		//setting();
		String teamName = "aa";
		String query = "select m from Member m inner join m.team t where t.name =:teamName";
		List<Object[]> members = em.createQuery(query).setParameter("teamName", teamName).getResultList();
		for (Object[] o: members) {
			System.out.println(((Member)o[0]).getName());
			System.out.println(((Team)o[1]).getName());
		}
	}
	@Test
	@Transactional
	@DisplayName("컬렉션값조호;")
	public void 컬렉션값조회(){
		setting();
		String query = "select t,m from Team t LEFT OUTER JOIN t.members m";
		List<Object[]> objects = em.createQuery(query).getResultList();
		for (Object[] o: objects) {
			System.out.println(((Team)o[0]).getName());
			System.out.println(((Member)o[1]).getName());
		}
	}

	public void setting(){
		Team team1 = new Team();
		team1.setName("aa");
		Team team2 = new Team();
		team2.setName("bb");
		Member member1 = new Member();
		member1.setName("1");
		member1.setTeam(team1);
		Member member2 = new Member();
		member2.setName("2");
		member2.setTeam(team2);
		Member member3 = new Member();
		member3.setName("3");
		member3.setTeam(team2);
		em.persist(team1);
		em.persist(team2);
		em.persist(member1);
		em.persist(member2);
		em.persist(member3);
	}



}
