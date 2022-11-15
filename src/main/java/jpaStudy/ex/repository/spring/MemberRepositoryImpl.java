package jpaStudy.ex.repository.spring;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jpaStudy.ex.dto.MemberSearchCondition;
import jpaStudy.ex.dto.MemberTeamDto;

import javax.persistence.EntityManager;
import java.util.List;

public class MemberRepositoryImpl implements MemberRepositoryCustom{
    private final JPAQueryFactory queryFactory;
    public MemberRepositoryImpl(EntityManager em){
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<MemberTeamDto> search(MemberSearchCondition condition) {
        return null;
    }
}
