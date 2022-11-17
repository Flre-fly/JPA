package jpaStudy.ex;

import jpaStudy.ex.entity.Food;
import jpaStudy.ex.entity.Member;
import jpaStudy.ex.entity.Team;
import jpaStudy.ex.repository.jpa.MemberJpaRepository;
import jpaStudy.ex.repository.nplus1.FoodRepository;
import jpaStudy.ex.repository.nplus1.TeamRepository;
import jpaStudy.ex.repository.spring.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.test.context.ActiveProfiles;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

@SpringBootTest
@ActiveProfiles("local")
public class nPlus1Test {
    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private FoodRepository foodRepository;

    @Autowired
    private EntityManager em;


    @Test
    @Transactional
    public void 즉시로딩지연로딩(){
        //given
        System.out.println("===============1");
        Member member = memberRepository.findById(1l).get();//찾아왔으니 영속성 컨텍스트에 저장해놓겠지\
        //쿼리가 왜 두개 나가지?
        System.out.println("===============2");
        member.getGroup1().getName();
        System.out.println("===============3");
        member.getTeam().getName();
        //when
        //then

    }

    @Test
    @Transactional
    public void findAll(){
        //given
        System.out.println("===============1");
        List<Member> memberList = memberRepository.findAll();
        em.clear();
        System.out.println("===============2");
        memberRepository.findById(1l);
        System.out.println("===============3");
        //when
        //then

    }

    @Test
    @Transactional
    public void n플러스1(){
        //given
        System.out.println("===============1");
        Member member = memberRepository.findById(1l).get();//찾아왔으니 영속성 컨텍스트에 저장해놓겠지\
        //쿼리가 왜 두개 나가지?
        System.out.println("===============2");
        teamRepository.findAll();
        System.out.println("===============3");
        //when
        //then

    }
    @Test
    @Transactional
    public void member_food(){
        //given
        System.out.println("===============1");
        Food food =foodRepository.findById(1l).get();//찾아왔으니 영속성 컨텍스트에 저장해놓겠지\
        //쿼리가 왜 두개 나가지?
        System.out.println("===============2");
        teamRepository.findAll();
        System.out.println("===============3");
        //when
        //then

    }


    @Test
    @Transactional
    public void emfindVSemcreatequery(){
        //given
        System.out.println("===============1");
        em.find(Member.class,1l);
        //left outer join해서 가져옴
        System.out.println("===============2");
        em.clear();
        // em.createQuery("select m from Member m where m.id = 1l")까지만 하면 쿼리를 안날리네..?
        em.createQuery("select m from Member m where m.id = 1l").getResultList();
        //left outer join이 아니라 하나씩 날려서 가져옴
        System.out.println("===============3");
        //when
        //then

    }
}
