package jpaStudy.ex;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jpaStudy.ex.dto.QMemberDto;
import jpaStudy.ex.entity.*;
import jpaStudy.ex.entity.sangsok.Person;
import jpaStudy.ex.entity.sangsok.Student;
import jpaStudy.ex.entity.sangsok.Teacher;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;

import javax.persistence.*;
import javax.persistence.criteria.*;
import javax.transaction.Transactional;
import java.util.List;

@SpringBootTest
class ExApplicationTests {

	@PersistenceContext
	private EntityManager em;
	@Autowired
	private MyRepository repository;
	@Autowired
	private JPAQueryFactory factory;
	@PersistenceUnit
	EntityManagerFactory emf;
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
	@Test
	@Transactional
	//회원이름 = 팀이름인 사람 수
	public void 그냥조인(){
		setting2();
		String query = "select count(m.id) from Member m left join m.team t where m.name = t.name";
		Object obj = em.createQuery(query).getResultList();
		System.out.println(obj.toString());
	}
	@Test
	@Transactional
	//회원이름 = 팀이름인 사람 수
	public void 세타조인(){
		setting2();
		String query = "select count(m.id) from Member m, Team t where m.name = t.name";
		Object obj = em.createQuery(query).getResultList();
		System.out.println(obj.toString());
	}


	//페치조인을 사용한다는 것은 지연로딩이아니라 그 즉시 member랑 연관된 team을 모두 가져오겠다는의미다
	//그래서 member.getTeam으로 접근해도 쿼리가 나가지 않을것이다
	//또한 준영속상태가 되어도 team을 얻어올수있다. 왜냐면프록시객체가 아니니까 초기화과정을 안거쳐도 돼서 초기화과정없이 바로 team조회가 가능하기 때문이다
	@Test
	@Transactional
	public void 페치조인(){
		setting2();
		String query = "select m from Member m join fetch m.team";
		List<Member> members = em.createQuery(query, Member.class).getResultList();
		for (Member member: members) {
			System.out.println(member.getName() + " " + member.getTeam().getName());
		}
	}
	@Test
	@Transactional
	public void 컬렉션패치조인(){
		setting();
		//그니까 em.creatquery를 하면
		//디비에쿼리를 날리는건 맞아요 근데
		//그것에 대한 결과(엔티티)가 1차캐시에 있으니 db에서 조회했으나 버리고 1차캐시껄 가져와요
		//하지만 1차캐시껀 members가 설정되지 않은 상태죠
		//그래서 조회가 안돼요!
		em.flush();
		em.clear();
		String query = "select t from Team t join fetch t.members where t.name =:name";
		List<Team> teams = em.createQuery(query, Team.class).setParameter("name", "bb").getResultList();
		for (Team team:teams) {
			System.out.println(team.getName());
			for (Member member:team.getMembers()) {
				System.out.println(member.getName());
			}
			System.out.println("============");
		}
	}

	@Test
	@Transactional
	public void 컬렉션패치조인_중복제거(){
		setting();
		em.flush();
		em.clear();
		//Team의 중복을 제거하라!
		String query = "select distinct t from Team t join fetch t.members where t.name =:name";
		List<Team> teams = em.createQuery(query, Team.class).setParameter("name", "bb").getResultList();
		for (Team team:teams) {
			System.out.println(team.getName());
			for (Member member:team.getMembers()) {
				System.out.println(member.getName());
			}
			System.out.println("============");
		}
	}
	@Test
	@Transactional
	public void 즉시로딩(){
		biSetting();
		em.flush();
		em.clear();//디비에 쿼리날리는것을 보고싶어서
		//팀이 n개면 n개의 팀에 대해 n개의 쿼리를 추가로 실행한다
		String query = "select t from Team t join t.members m";
		List<Team> teams = em.createQuery(query, Team.class).getResultList();
		for (Team team:teams) {
			System.out.println(team.getName());
			for (Member member:team.getMembers()) {
				System.out.println(member.getName());
			}
			System.out.println("============");
		}
	}
	@Test
	@Transactional
	//실패하는 test
	public void 둘이상컬렉션을페치(){
		biSettingWithFood();
		String query = "select t from Team t join fetch t.members join fetch t.foods";
		//엔티티를 fetch하는건 괜찮은데 저런 컬렉션 둘 이상을 fetch하는게 안돼
		List<Team> teams = em.createQuery(query, Team.class).getResultList();
		for (Team team:teams) {
			System.out.println(team.getName());
			for (Member member:team.getMembers()) {
				System.out.println(member.getName());
			}
			System.out.println("============");
			for (Food food : team.getFoods()) {
				System.out.println(food.getName());
			}
		}
	}
	@Test
	@Transactional
	public void 둘이상의페치조인(){
		biSettingWithGroup();
		//이땐 group1, team, member 모두 다 존재해야 출력이 되네..?[-
		String query = "select m from Member m join fetch m.team join fetch m.group1";
		List<Member> members = em.createQuery(query, Member.class).getResultList();
		for (Member mem:members) {
			System.out.println(mem.getName() + " " + mem.getTeam().getName() + " " + mem.getGroup1().getName());
		}
	}
	@Test
	@Transactional
	public void 컬렉션페치조인은페이징처리가안돼(){
		biSetting();
		String str = "select t from Team t join fetch t.members m";
		TypedQuery<Team> query =  em.createQuery(str, Team.class);
		query.setFirstResult(10);
		query.setMaxResults(20);
		List<Team> teams = query.getResultList();
		for (Team team:teams) {
			System.out.println(team.getName());
			for (Member member:team.getMembers()) {
				System.out.println(member.getName());
			}
			System.out.println("============");
		}

	}

	@Test
	@Transactional
	public void 상속(){
		//given
		Student student = new Student(12, "학생1");
		Teacher teacher = new Teacher("교감", "김교감");
		em.persist(student);
		em.persist(teacher);
		em.flush();
		em.clear();
		//when
		List<Person> personList = em.createQuery("select p from Person p where Type(p) = Student", Person.class).getResultList();
		//then
		for (Person p: personList) {
			System.out.println(p.getName() + " " + ((Student) p).getAge());
		}
	}

	@Test
	@Transactional
	public void null비교(){
		//given
		Student s = new Student();
		s.setAge(1);
		em.persist(s);
		//when
		List<Student> list = em.createQuery("select s from Student s where s.name is null", Student.class).getResultList();
		//List<Student> list = em.createQuery("select s from Student s where s.name = null", Student.class).getResultList(); // error!
	}

	@Test
	@Transactional
	public void 컬렉션식ISEMPTY(){
		//given
		biSetting();
		//when
		List<Team> list = em.createQuery("select t from Team t where t.members is empty", Team.class).getResultList();
		//then
		for (Team team:list) {
			System.out.println(team.getName());
		}
	}
	@Test
	@Transactional
	public void 출력안되는컬렉션식of(){
		//given
		biSetting();
		Member member1 = new Member();
		member1.setName("mem1");
		em.persist(member1);//member1도 영속화되어있어야 되는듯
		//when
		List<Team> list = em.createQuery("select t from Team t where :member1 member of t.members", Team.class).setParameter("member1", member1).getResultList();//주솟값이 다르니 출력이 안됨
		//then
		for (Team team:list) {
			System.out.println(team.getName());
		}
	}

	@Test
	@Transactional
	public void 출력되는컬렉션식of(){
		//given
		Member member1 = new Member();
		member1.setName("mem1");
		Team team1 = new Team();
		team1.setName("team1");
		team1.addMember(member1);
		em.persist(member1);//member1도 영속화되어있어야 되는듯
		em.persist(team1);
		//when
		List<Team> list = em.createQuery("select t from Team t where :member1 member of t.members", Team.class).setParameter("member1", member1).getResultList();
		//then
		for (Team team:list) {
			System.out.println(team.getName());
		}
	}
	@Test
	@Transactional
	public void 임베디드타입은비교지원안함(){
		//given
		Address address1 = new Address("주소1","주소2");
		Address address2 = new Address("주소1","주소2");
		Member member = new Member();
		member.setName("홍길동");
		member.setMyAddress(address1);
		em.persist(member);
		//when
		List<Member> members = em.createQuery("select m from Member m where :address = m.myAddress", Member.class).setParameter("address", address2).getResultList();
		//then
		for (Member mem:members) {
			System.out.println(mem.getName() + " " + mem.getMyAddress().getCode() + " " + mem.getMyAddress().getZipCode());
		}

	}
	@Test
	@Transactional
	public void null연산(){
		//given
		Member member = new Member();
		em.persist(member);
		//when
		//null은 = 연산이 안돼서 오류가뜬다 m.name = :name 이 null = null이니까
		List<Member> members = em.createQuery("select m from Member m where (m.name = :name) is null").setParameter("name", null).getResultList();
		for (Member mem:members) {
			System.out.println(mem.getId());
		}
	}

	@Test
	@Transactional
	public void Named쿼리(){
		biSetting();
		List<Member> list = em.createNamedQuery("Member.findByUsername", Member.class).setParameter("name", "mem1").getResultList();
		for (Member mem:list) {
			System.out.println(mem.getName());
		}
	}

	@Test
	@Transactional
	public void criteria() {
		biSetting();
		//select m from Member m
		//cb필요
		CriteriaBuilder cb = em.getCriteriaBuilder();
		//반환타입지정
		CriteriaQuery<Member> cq = cb.createQuery(Member.class);
		Root<Member> m = cq.from(Member.class);
		cq.select(m);
		//그대로
		List<Member> members = em.createQuery(cq).getResultList();
		for (Member mem:members) {
			System.out.println(mem.getName());
		}
	}
	@Test
	@Transactional
	public void criteria_조건정렬(){
		//given
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Member> cq = cb.createQuery(Member.class);//반환값은 엔티티, 임베디드타입, 기타에 쓸 수 있다

		Root<Member> m = cq.from(Member.class);//조회의 시작점 엔티티에만 사용될 수 있다
		Predicate usernameEqual = cb.equal(m.get("name"), "a");
		Order ageDesc = cb.desc(m.get("age"));
		cq.select(m)
				.where(usernameEqual)
				.orderBy(ageDesc);
		List<Member> members = em.createQuery(cq).getResultList();
	}

	@Test
	@Transactional
	public void criteria_조건정렬2(){
		//10살을 초과하는 회원 조회, 나이 역순으로 정렬
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Member> cq = cb.createQuery(Member.class);
		Root<Member> m = cq.from(Member.class);
		Predicate greaterThan10 = cb.greaterThan(m.get("age"), 10);
		Order ageDesc = cb.desc(m.get("age"));
		cq.select(m).where(greaterThan10).orderBy(ageDesc);
		List<Member> members = em.createQuery(cq).getResultList();
	}

	@Test
	@Transactional
	public void 쿼리DSL(){
		biSetting();
		QMember member = new QMember("m");
		QMember member1 = QMember.member;
		List<Member> members =
				factory.selectFrom(member)
						.where(member.id.lt(10l))
						.orderBy(member.id.desc()).fetch();
		for (Member mem:members) {
			System.out.println(mem.getName());
		}
	}

	@Test
	@Transactional
	public void 쿼리DSL페이징(){
		biSetting();
		QMember member = new QMember("m");
		List<Member> members =
				factory.selectFrom(member)
						.where(member.id.lt(10l))
						.orderBy(member.id.desc())
						.offset(1l)//페이지 번호
						.limit(3)//페이지 사이즈
						.fetch();
		for (Member mem:members) {
			System.out.println(mem.getName());
		}

		factory.selectFrom(member)
				.orderBy(member.id.desc(), member.name.asc().nullsLast()).fetch();
	}
	@Test
	@Transactional
	public void 쿼리dsl집합함수(){
		biSetting();
		QMember member = QMember.member;
		List<Tuple> tuples = factory.select(member.id.count(), member.id.avg())
				.from(member)
				.fetch();
		for (Tuple t:tuples) {
			System.out.println(t.get(member.id.count()));
			System.out.println(t.get(member.id.avg()));
		}
	}
	@Test
	@Transactional
	public void 쿼리dsl기본조인(){
		biSetting();
		QMember member = QMember.member;
		QTeam team = QTeam.team;
		List<Member> members = factory.selectFrom(member).join(member.team, team)
				.where(team.name.eq("team1"))
				.fetch();
		//TEAM1이랑 조인된 MEMBER의 이름을 말하는거니까...
		Assertions.assertThat(members).extracting("name").containsAnyOf("mem1","mem2");

	}
	@Test
	@Transactional
	public void 쿼리dsl세타조인(){
		setting2();
		QMember Qmem = QMember.member;
		QTeam Qteam = QTeam.team;
		List<Member> list = factory.select(Qmem).from(Qmem, Qteam).where(Qmem.name.eq(Qteam.name)).fetch();
		for (Member mem:list) {
			System.out.println(mem.getName() + " " + mem.getTeam().getName());
		}
	}
	@Test
	@Transactional
	public void outerjoin_innerjoin(){
		QMember member = QMember.member;
		QTeam team = QTeam.team;
		List<Team> list = factory.selectFrom(team).leftJoin(member)
				.on(team.id.eq(member.team.id)).fetch();
		//on: join전에 필터링하고 조인을 하면 에러안남
		//where: join을 했음. 그다음에 필터링하면 에러남 -> null 때문에 그런가?
		list.stream().forEach(e -> {
				System.out.println(e.getName() + "========");
				e.getMembers().stream().forEach(System.out::println);
		});
		/*
		for (Team t:list) {
			System.out.println(t.getName() + "========");
			for (Member m:t.getMembers()) {
				System.out.println(m.getName());
			}
		}*/
	}
	@Test
	@Transactional
	@DisplayName("예) 회원과 팀을 조인하면서, 팀 이름이 TeamA인 팀만 조인 하고 회원은 모두 조회한다.")
	void joinOnFiltering(){
		//given
		biSetting();
		QMember member = QMember.member;
		QTeam team = QTeam.team;
		//when
		List<Tuple> result = factory
				.select(member, team)
				.from(member)
				.leftJoin(member.team, team)
				.where(team.name.eq("team1"))
				.fetch();
		//then
		for (Tuple tuple : result) {
			System.out.print(tuple.get(member).getName());
			if(tuple.get(team)!=null) System.out.println("   "+tuple.get(team).getName());
			else System.out.println("  null");
		}
	}
	@Test
	@Transactional
	@DisplayName("패치조인을 사용하지 않고 데이터를 가지고 오는 방법이다.")
	void fetchJoinNo(){
		//given
		biSetting();
		em.flush();
		em.clear();
		//when
		Member member = factory.selectFrom(QMember.member).where(QMember.member.name.eq("mem1")).fetchOne();
		boolean isLoaded = emf.getPersistenceUnitUtil().isLoaded(member.getTeam());
		//then
		Assertions.assertThat(isLoaded).isEqualTo(false);
	}

	@Test
	@Transactional
	@DisplayName("패치조인을 사용해서 데이터를 가지고 오는 방법이다.")
	void fetchJoinYes(){
		//given
		biSetting();
		em.flush();
		em.clear();
		//when
		Member member = factory.selectFrom(QMember.member).join(QMember.member.team, QTeam.team).fetchJoin().where(QMember.member.name.eq("mem1")).fetchOne();
		boolean isLoaded = emf.getPersistenceUnitUtil().isLoaded(member.getTeam());
		//then
		Assertions.assertThat(isLoaded).isEqualTo(true);
	}
	@Test
	@Transactional
	@DisplayName("나이가 가장 많은 회원을 조회한다고 해보자.")
	void subQuery(){
		//given
		biSetting();
		QMember m2 = new QMember("M");
		//when
		Member member = factory.selectFrom(QMember.member).where(QMember.member.age.eq(JPAExpressions.select(m2.age.max()).from(m2).fetchOne())).fetchOne();
		//then
		Assertions.assertThat(member.getAge()).isEqualTo(113);
	}
	@Test
	@Transactional
	@DisplayName("나이가 평균 이상인 회원을 조회해오자")
	void subQueryWithBiggerthanAvg(){
		//given
		biSetting();
		QMember m2 = new QMember("M");
		//when
		List<Member> members = factory.selectFrom(QMember.member).where(QMember.member.age.goe(JPAExpressions.select(m2.age.avg()).from(m2))).fetch();
		//then
		members.forEach(e->
		System.out.println(e.getName()));
	}

	@Test
	@Transactional
	@DisplayName("나이가 10인 회원을 조회한다고 해보자. - In 을 이용")
	void subQueryIn(){
		//given
		biSetting();
		QMember m2 = new QMember("M");
		//when
		Member member = factory.selectFrom(QMember.member).where(QMember.member.in(JPAExpressions.selectFrom(m2).where(m2.age.eq(1)))).fetchOne();
		//then
		Assertions.assertThat(member.getAge()).isEqualTo(1);
	}


	@Transactional
	@Test
	void baseCase(){
		//given
		biSetting();
		//when
		List<String> agelist = factory.select(QMember.member.age.when(1).then("한살이다!!!").otherwise("난모르겠다!!")).from(QMember.member).fetch();
		//then
		agelist.forEach(System.out::println);
	}
	@Test
	@Transactional
	@DisplayName("조금 복잡한 Case 문! - CaseBuilder() 이용!")
	void complexCase(){
		//given
		biSetting();
		//when
		List<String> ageList = factory.select(new CaseBuilder().when(QMember.member.age.between(0, 20)).then("0~20사이의 나이군!").otherwise("그외의나이군!")).from(QMember.member).fetch();
		//then
		ageList.forEach(System.out::println);
	}
	@Test
	@Transactional
	//0 ~ 30살이 아닌 회원을 가장 먼저 출력
	//0 ~ 20살 회원 출력
	//21 ~ 30살 회원 출력
	void complexCase2(){
		//given
		biSetting();
		//when
		NumberExpression<Integer> rankCase = new CaseBuilder().when(QMember.member.age.between(1,2)).then(2)
				.when(QMember.member.age.between(3,20)).then(1).otherwise(3);
		List<Tuple> tuple = factory.select(QMember.member.name, QMember.member.age, rankCase).from(QMember.member).orderBy(rankCase.desc()).fetch();
		//then
		tuple.forEach(System.out::println);
	}

	@Test
	@Transactional
	void addConstant(){
		//given
		biSetting();
		//when
		List<Tuple> list = factory.select(QMember.member.name, Expressions.constant("상수1")).from(QMember.member).fetch();
		//then
		list.forEach(System.out::println);
	}
	@Test
	@Transactional
	void addConcat(){
		//given
		biSetting();
		//when
		List<Tuple> list = factory.select(QMember.member.name, QMember.member.name.concat("_").concat(QMember.member.age.stringValue())).from(QMember.member).fetch();
		//then
		list.forEach(System.out::println);
	}

	@Test
	//기존 jpa를 이용해서 projection을 해보자!
	void projectionWithJpa(){
		//given
		//when
		List<jpaStudy.ex.dto.MemberDto> result = em.createQuery("select new jpaStudy.ex.dto.MemberDto(m.name, m.age) from Member m", jpaStudy.ex.dto.MemberDto.class).getResultList();
		//then
		result.forEach(e -> {
			System.out.println(e.getName() + " " + e.getAge());
		});
		//언제 다 생성자에 맞춰 다 넣어줘!!!!!!!!!!!!!!!!!!!!!!!!!

	}
	@Test
	@DisplayName("프로퍼티 setter 를 이용해 생성하는 방식")
	void projectionWithQueryDSL(){
		//given
		//when
		//이렇게 인자로 넘기는 parameter의 순서를 바꾸면 문제가 생김
		//List<jpaStudy.ex.dto.MemberDto> list = factory.select(Projections.constructor(jpaStudy.ex.dto.MemberDto.class, QMember.member.age, QMember.member.name.as("name") )).from(QMember.member).fetch();
		List<jpaStudy.ex.dto.MemberDto> list = factory.select(Projections.constructor(jpaStudy.ex.dto.MemberDto.class, QMember.member.name.as("name") , QMember.member.age)).from(QMember.member).fetch();
		//then
		list.forEach(e -> {
			System.out.println(e.getName() + " " + e.getAge());
		});
	}
	@Test
	void 서브쿼리_projection(){
		//given
		QMember member1 = new QMember("M");
		//when
		List<jpaStudy.ex.dto.MemberDto> result = factory.select(Projections.fields(jpaStudy.ex.dto.MemberDto.class, QMember.member.name,
				ExpressionUtils.as(
						JPAExpressions.select(member1.age.max()).from(member1), "age"
				)
				)).from(QMember.member).fetch();
		//then
		result.forEach(e -> {
			System.out.println(e.getName() + " " + e.getAge());
			Assertions.assertThat(e.getAge()).isEqualTo(113);
		});
	}
	@Test
	void QueryProjection(){
		//given
		//when
		List<jpaStudy.ex.dto.MemberDto> result = factory.select(new QMemberDto( QMember.member.name, QMember.member.age )).from(QMember.member).fetch();
		//then
		result.forEach(e -> {
			System.out.println(e.getName() + " " + e.getAge());
		});
	}

	@Test
	@DisplayName("조건에 따라서 조건문이 실행되기도 하고 안실행되기도 하는 쿼리")
	void dynamicQueryUsingBooleanBuilder(){
		//given
		String username = null;
		Integer age = 1;
		//when
		List<Member> list = searchMember1(username, age);
		//then
		list.forEach(e -> {
			System.out.println(e.getName() + " " + e.getAge());
		});

	}
	List<Member> searchMember1(String name, Integer age){
		BooleanBuilder b = new BooleanBuilder();
		if(name != null){
			b.and(QMember.member.name.eq(name));
		}
		if(age != null){
			b.and(QMember.member.age.goe(age));
		}
		return factory.selectFrom(QMember.member).where(b).fetch();

	}
	@Test
	@DisplayName("Where 다중 파라미터 사용")
	void dynamicQueryUsingWhere(){
		//given
		String username = "mem1";
		Integer age = null;
		//when
		List<Member> list = searchMember2(username, age);
		//then
		list.forEach(e -> {
			System.out.println(e.getName() + " " + e.getAge());
		});
	}
	List<Member> searchMember2(String userName, Integer age){
		return factory.selectFrom(QMember.member).where(userNameEp(userName), ageEp(age)).fetch();
	}
	private com.querydsl.core.types.Predicate userNameEp(String username){
		if(username==null) return null;
		return QMember.member.name.eq(username);
	}
	private com.querydsl.core.types.Predicate ageEp(Integer age){
		if(age==null) return null; //조건검사안함
		return QMember.member.age.lt(112321l);
	}
	@Test
	@Transactional
	void bulk(){
		//given
		//when
		long count = factory.update(QMember.member).set(QMember.member.name, "비회원").execute();
		//then
		Assertions.assertThat(count).isEqualTo(7);
	}

	@Test
	@Transactional
	void bulk주의사항(){
		//given
		//when
		List<Member> list1 = factory.selectFrom(QMember.member).fetch();
		long count = factory.update(QMember.member).set(QMember.member.name, "비회원").execute();
		List<Member> list = factory.selectFrom(QMember.member).fetch();
		//then
		Assertions.assertThat(count).isEqualTo(4);
		list.forEach(e -> {
			System.out.println(e.getName() + " " + e.getAge());
		});
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

	public void setting2(){
		Team team1 = new Team();
		team1.setName("a");
		Team team2 = new Team();
		team2.setName("c");
		Team team3 = new Team();
		team3.setName("d");
		Member member1 = new Member();
		member1.setName("b");
		member1.setTeam(team1);
		Member member2 = new Member();
		member2.setName("a");
		member2.setTeam(team2);
		Member member3 = new Member();
		member3.setName("d");
		member3.setTeam(team3);
		em.persist(team1);
		em.persist(team2);
		em.persist(team3);
		em.persist(member1);
		em.persist(member2);
		em.persist(member3);
	}
	@Commit
	@Transactional
	@Test
	public void biSetting(){
		Team team1 = new Team();
		team1.setName("team1");
		Team team2 = new Team();
		team2.setName("team2");
		Team team3 = new Team();
		team3.setName("team3");
		Team team4 = new Team();
		team4.setName("team4");

		Member member1 = new Member();
		member1.setName("mem1");
		member1.setAge(1);

		Member member2 = new Member();
		member2.setName("mem2");
		member2.setAge(12);

		Member member3 = new Member();
		member3.setName("mem3");
		member3.setAge(113);

		Member member4 = new Member();
		member4.setName("mem4");

		team1.addMember(member1);
		team1.addMember(member2);
		team2.addMember(member3);
		team4.addMember(member4);

		em.persist(team1);
		em.persist(team2);
		em.persist(team3);
		em.persist(team4);
		em.persist(member1);
		em.persist(member2);
		em.persist(member3);
		em.persist(member4);
	}
	public void biSettingWithFood(){
		Team team1 = new Team();
		team1.setName("team1");
		Team team2 = new Team();
		team2.setName("team2");
		Team team3 = new Team();
		team3.setName("team3");

		Member member1 = new Member();
		member1.setName("mem1");

		Member member2 = new Member();
		member2.setName("mem2");

		Member member3 = new Member();
		member3.setName("mem3");

		Food food1 = new Food();
		food1.setName("food1");

		team1.addMember(member1);
		team1.addMember(member2);
		team2.addMember(member3);
		team1.addFood(food1);

		em.persist(team1);
		em.persist(team2);
		em.persist(team3);
		em.persist(member1);
		em.persist(member2);
		em.persist(member3);
	}
	public void biSettingWithGroup(){
		Team team1 = new Team();
		team1.setName("team1");
		Team team2 = new Team();
		team2.setName("team2");
		Team team3 = new Team();
		team3.setName("team3");

		Member member1 = new Member();
		member1.setName("mem1");

		Member member2 = new Member();
		member2.setName("mem2");

		Member member3 = new Member();
		member3.setName("mem3");

		Group1 group1 = new Group1();
		group1.setName("group1");
		member1.setGroup1(group1);
		member2.setGroup1(group1);
		team1.addMember(member1);
		team1.addMember(member2);
		team2.addMember(member3);

		em.persist(team1);
		em.persist(team2);
		em.persist(team3);
		em.persist(group1);
		em.persist(member1);
		em.persist(member2);
		em.persist(member3);

	}



}
