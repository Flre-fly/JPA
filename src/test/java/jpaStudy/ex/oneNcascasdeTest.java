package jpaStudy.ex;

import jpaStudy.ex.entity.Member;
import jpaStudy.ex.entity.Team;
import jpaStudy.ex.entity.Users;
import jpaStudy.ex.entity.UserDetails;
import jpaStudy.ex.repository.UserRepository;
import jpaStudy.ex.repository.nplus1.TeamRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@SpringBootTest
public class oneNcascasdeTest {
    @Autowired
    UserRepository userRepository;

    @Autowired
    TeamRepository teamRepository;

    @Autowired
    EntityManager em;
    @Test
    @Transactional
    public void test(){
        //given
        System.out.println("-------1");
        Users user1 = new Users("name");
        UserDetails detail1 = new UserDetails("hi");
        UserDetails detail2 = new UserDetails("bye");
        user1.addDetail(detail1);
        user1.addDetail(detail2);
        userRepository.save(user1);
        //when
        System.out.println("-------2");
        //then
    }

    @Test
    @Transactional
    public void 저장하고나서add(){
        //given
        System.out.println("-------1");
        Users user1 = new Users("name");

        userRepository.save(user1);
        System.out.println(em.contains(user1));//진짜 ㄹㅇ 없었던애인 경우엔 user1자체를 영속화시키고 준영속이었던 놈은 return값에 진짜를 내놓는다


        UserDetails detail1 = new UserDetails("hi");
        UserDetails detail2 = new UserDetails("bye");
        user1.addDetail(detail1);
        user1.addDetail(detail2);
        //when
        System.out.println("-------2");
        //then
    }
    @Test
    @Transactional
    public void 저장하고나서add2(){
        //given
        System.out.println("-------1");
        Users user1 = new Users("name");
        userRepository.save(user1);//영속화됨
        UserDetails detail1 = new UserDetails("hi");
        UserDetails detail2 = new UserDetails("bye");
        user1.addDetail(detail1);
        user1.addDetail(detail2);
        //when
        System.out.println("-------2");
        //then
        em.flush();//이거 추가안하면 쿼리안날라감. 커밋 어노테이션이 없어서 그런듯
    }

}
