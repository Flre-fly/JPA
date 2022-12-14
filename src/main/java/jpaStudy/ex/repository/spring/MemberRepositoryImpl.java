package jpaStudy.ex.repository.spring;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jpaStudy.ex.dto.MemberSearchCondition;
import jpaStudy.ex.dto.MemberTeamDto;
import jpaStudy.ex.dto.QMemberTeamDto;
import jpaStudy.ex.entity.QMember;
import jpaStudy.ex.entity.QTeam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import javax.persistence.EntityManager;
import java.util.List;

import static org.springframework.util.StringUtils.hasText;

public class MemberRepositoryImpl implements MemberRepositoryCustom{
    private final JPAQueryFactory queryFactory;
    public MemberRepositoryImpl(EntityManager em){
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<MemberTeamDto> search(MemberSearchCondition condition){
        return queryFactory.select(new QMemberTeamDto(
                        QMember.member.id.as("memberId"),
                        QMember.member.name.as("username"),
                        QMember.member.age,
                        QTeam.team.id.as("teamId"),
                        QTeam.team.name.as("teamName")
                )).from(QMember.member)
                .join(QMember.member.team, QTeam.team)
                .where(
                        memberNameEq(condition.getUsername()),
                        teamNameEq(condition.getTeamName()),
                        ageLoe(condition.getAgeLoe()),
                        ageGoe(condition.getAgeGoe())
                )
                .fetch();
    }

    @Override
    public Page<MemberTeamDto> searchPageSimple(MemberSearchCondition condition, Pageable pageable) {
        List<MemberTeamDto> content = queryFactory.select(new QMemberTeamDto(
                        QMember.member.id.as("memberId"),
                        QMember.member.name.as("username"),
                        QMember.member.age,
                        QTeam.team.id.as("teamId"),
                        QTeam.team.name.as("teamName")
                )).from(QMember.member)
                .join(QMember.member.team, QTeam.team)
                .where(
                        memberNameEq(condition.getUsername()),
                        teamNameEq(condition.getTeamName()),
                        ageLoe(condition.getAgeLoe()),
                        ageGoe(condition.getAgeGoe())
                )
                .fetch();
        return new PageImpl<>(content, pageable, content.size());
    }

    @Override
    public Page<MemberTeamDto> searchPageComplex(MemberSearchCondition condition, Pageable pageable) {
        List<MemberTeamDto> contents = queryFactory.select(new QMemberTeamDto(
                        QMember.member.id.as("memberId"),
                        QMember.member.name.as("username"),
                        QMember.member.age,
                        QTeam.team.id.as("teamId"),
                        QTeam.team.name.as("teamName")
                )).from(QMember.member)
                .join(QMember.member.team, QTeam.team)
                .where(
                        memberNameEq(condition.getUsername()),
                        teamNameEq(condition.getTeamName()),
                        ageLoe(condition.getAgeLoe()),
                        ageGoe(condition.getAgeGoe())
                ).fetch();
        JPAQuery<MemberTeamDto> countQuery = queryFactory.select(new QMemberTeamDto(
                        QMember.member.id.as("memberId"),
                        QMember.member.name.as("username"),
                        QMember.member.age,
                        QTeam.team.id.as("teamId"),
                        QTeam.team.name.as("teamName")
                )).from(QMember.member)
                .join(QMember.member.team, QTeam.team)
                .where(
                        memberNameEq(condition.getUsername()),
                        teamNameEq(condition.getTeamName()),
                        ageLoe(condition.getAgeLoe()),
                        ageGoe(condition.getAgeGoe())
                );
        //return new PageImpl<>(contents, pageable, total);
        //return PageableExecutionUtils.getPage(contents, pageable, countQuery::fetchCount);
        //fetchcount??????????????? ????????? fetch().size()??? ??????????????????
        return  new PageImpl<>(contents, pageable, countQuery.fetch().size());
    }

    private BooleanExpression memberNameEq(String name){
        if (!hasText(name)) return null;
        return QMember.member.name.eq(name);
    }
    private  BooleanExpression teamNameEq(String name){
        if (!hasText(name)) return null;
        return QTeam.team.name.eq(name);
    }
    private  BooleanExpression ageLoe(Integer age){
        if(age== null) return null;
        return QMember.member.age.loe(age);
    }
    private  BooleanExpression ageGoe(Integer age){
        if(age== null) return null;
        return QMember.member.age.goe(age);
    }
}
