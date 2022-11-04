package jpaStudy.ex;

import jpaStudy.ex.entity.Member;
import jpaStudy.ex.entity.Team;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@SpringBootTest
public class DetachedTest {
    @PersistenceContext
    private EntityManager em;
    @Test
    @Transactional
    public void 준영속예제1(){
        //given
        String originName = "origin";
        Member member = Member.builder().name(originName).id(1l).build();//이 멤버는 영컨에도 db에도 없다.
        //when
        em.merge(member);//준영속상태인 member를 merge시킨다 id값이 영컨에도 디비에도 없으니 persist와 같이 작동한다
        em.flush();
        em.clear();
        Member persistedMember = em.find(Member.class, member.getId());//member를 얻어온다
        //then
        Assertions.assertNotNull(persistedMember.getId());
        Assertions.assertEquals(originName, persistedMember.getName());
    }
    @Test
    @Transactional
    public void 준영속예제2(){
        //given
        String originName = "origin";
        Member member = Member.builder().name(originName).id(10l).build();//내가 2l로 설정해줌
        //when
        em.merge(member);
        em.flush();
        em.clear();
        //then
        Member findMember = em.find(Member.class, 10l);//10l으로 설정해줬지만
        //id autoincrese를 따르기때문에 10l으로 찾으면 안뜸
        Assertions.assertNotNull(findMember);

    }

    @Test
    @Commit
    @Transactional
    public void 참조무결성위반(){
        //given
        Member member = Member.builder().build();
        Team team = new Team();
        team.addMember(member);
        em.persist(member);
        em.persist(team);
        em.flush();
        em.clear();
        //then
        team=em.find(Team.class, team.getId());
        em.remove(team);
        //then
        //PersistenceException
        //member말고 fk인 team을 제거해야 터짐



    }


}
