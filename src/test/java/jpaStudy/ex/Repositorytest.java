package jpaStudy.ex;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jpaStudy.ex.dto.MemberSearchCondition;
import jpaStudy.ex.dto.MemberTeamDto;
import jpaStudy.ex.entity.Member;
import jpaStudy.ex.entity.Team;
import jpaStudy.ex.repository.jpa.MemberJpaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.List;

@SpringBootTest
public class Repositorytest {
    @Autowired
    private MemberJpaRepository repository;

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
    void 동적쿼리where(){
        //given
        //when
        List<MemberTeamDto> list = repository.searchByWhere(new MemberSearchCondition(null, null , null,null));
        //then
        list.forEach(e -> {
            System.out.println(e.getUsername() + " " + e.getTeamName() + " " + e.getTeamId() + " " + e.getMemberId() + " " + e.getAge());
        });
    }
    @Test
    @Transactional
        // 왜이거안붙이면 프록시초기화안됐다더?  그이유는 트랜잭션안에있어야 영속성컨텍스트의 관리를바당서그런건가"?
    //repository에 붙은 트랜잭션ㅇ이 끝나면서 프록시 객체인 team의 name을 읽으려해서 그런거임
    void 왜네번이나출력되지(){//헤결
        //given
        //when
        List<Member> list = repository.test(new MemberSearchCondition("mem1", null , null,null));
        //then
        list.forEach(e -> {
            System.out.println(e.getName() + " " + e.getTeam().getName() + " " + e.getId() + " " );
        });
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
