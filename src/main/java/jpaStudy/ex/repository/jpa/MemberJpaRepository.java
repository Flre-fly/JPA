package jpaStudy.ex.repository.jpa;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jpaStudy.ex.dto.MemberSearchCondition;
import jpaStudy.ex.dto.MemberTeamDto;
import jpaStudy.ex.dto.QMemberTeamDto;
import jpaStudy.ex.entity.Member;
import jpaStudy.ex.entity.QMember;
import jpaStudy.ex.entity.QTeam;
import org.springframework.boot.autoconfigure.info.ProjectInfoProperties;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

import static org.springframework.util.StringUtils.hasText;

@org.springframework.stereotype.Repository
public class MemberJpaRepository {
    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    public MemberJpaRepository(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }
    //save, findbyid, findByusername, findAll
    public void save(Member member){
        em.persist(member);
    }
    public Optional<Member> findById(Long id){
        //Member member = queryFactory.selectFrom(QMember.member).where(QMember.member.id.eq(id)).fetchOne();
        //이런건 em으로 처리하고 복잡한것만 factory 사용!
        Member member = em.find(Member.class, id);
        return Optional.ofNullable(member);
    }
    public Optional<Member> findByName(String name){
        Member member = queryFactory.selectFrom(QMember.member).where(QMember.member.name.eq(name)).fetchOne();
        return Optional.ofNullable(member);
    }
    public List<Member> findAll(){
        return queryFactory.selectFrom(QMember.member).fetch();
    }
    public List<MemberTeamDto> searchByBuilder(MemberSearchCondition condition){
        BooleanBuilder builder = new BooleanBuilder();
        if(hasText(condition.getUsername())){
            builder.and(QMember.member.name.eq(condition.getUsername()));
        }
        if(hasText(condition.getTeamName())){
            builder.and(QMember.member.team.name.eq(condition.getTeamName()));
        }
        if(condition.getAgeLoe() != null){
            builder.and(QMember.member.age.goe(condition.getAgeLoe()));
        }
        if(condition.getAgeGoe() != null){
            builder.and(QMember.member.age.loe(condition.getAgeGoe()));
        }
        return queryFactory.select(new QMemberTeamDto(
                QMember.member.id.as("memberId"),
                QMember.member.name.as("username"),
                QMember.member.age,//생성자 순서에 잘 맞춰야함
                QTeam.team.id.as("teamId"),
                QTeam.team.name.as("teamName")
        )).from(QMember.member).leftJoin(QMember.member.team, QTeam.team).on(builder).fetch();
    }
    //on과 where의 차이, join과 outer join의 차이
    //leftjoin으로 해야 다 출력이되니까. null이여도 안걸리고 출력이 되니까 그렇게 한거야!

}
