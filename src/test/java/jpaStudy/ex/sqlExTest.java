package jpaStudy.ex;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jpaStudy.ex.entity.Member;

import jpaStudy.ex.entity.QMember;
import static jpaStudy.ex.entity.QMember.member;

import jpaStudy.ex.entity.Station;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.util.Assert;


import java.util.List;

//복습
@ActiveProfiles("local")
@SpringBootTest
public class sqlExTest {

    @Autowired
    private JPAQueryFactory factory;
    @Autowired
    Station station;



    @Test
    public void 중복x(){

        List<Member> members = factory.selectFrom(QMember.member).orderBy(QMember.member.id.desc()).fetch();
        members.forEach(e->{
            System.out.println(e.getName() + " " + e.getId());
        });
    }
    @Test
    public void 이름같은문자두번(){
        factory.selectFrom(QMember.member).where(QMember.member.name.like("%L%L%").and(QMember.member.age.eq(22))).fetch();
    }
    @Test
    public void 프로파일(){
        Assertions.assertEquals(station.getName(), "testStation");
        //https://bepoz-study-diary.tistory.com/371

    }


}
