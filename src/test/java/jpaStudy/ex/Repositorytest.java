package jpaStudy.ex;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jpaStudy.ex.dto.MemberSearchCondition;
import jpaStudy.ex.dto.MemberTeamDto;
import jpaStudy.ex.entity.Member;
import jpaStudy.ex.entity.QMember;
import jpaStudy.ex.entity.Team;
import jpaStudy.ex.repository.jpa.MemberJpaRepository;
import jpaStudy.ex.repository.spring.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import javax.transaction.Transactional;
import java.util.List;

@SpringBootTest
@ActiveProfiles("local")
public class Repositorytest {
    @Autowired
    private MemberJpaRepository repository;
    @Autowired
    MemberRepository memberRepository;

    @Autowired
    JPAQueryFactory queryFactory;
    @Test
    void 동적쿼리빌더(){
        //given
        //when
        List<MemberTeamDto> list = repository.searchByBuilder(new MemberSearchCondition("mem1", null , null,null));
        //then
        list.forEach(e -> {
            System.out.println(e.getUsername() + " " + e.getTeamName() + " " + e.getTeamId() + " " + e.getMemberId() + " " + e.getAge());
        });
    }
    @Test
    @Transactional
    void 동적쿼리where(){
        //given
        biSetting();
        //when
        List<MemberTeamDto> list = repository.searchByWhere(new MemberSearchCondition(null, "team1" , null,null));
        //then
        System.out.println("=======================================");
        list.forEach(e -> {
            System.out.println(e.getUsername() + " " + e.getTeamName() + " " + e.getTeamId() + " " + e.getMemberId() + " " + e.getAge());
        });
    }

    @Test
    void querydslPredicateExecutorTest() {
        //given
        //whem
        Iterable<Member> result = memberRepository.findAll(
                QMember.member.age.between(0, 40)
                        .and(QMember.member.name.eq("member1"))
        );
        //then
        System.out.println("--------------");
        result.forEach(System.out::println);

    }


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

        repository.save(member1);
        repository.save(member2);
        repository.save(member3);
        repository.save(member4);

    }
}
