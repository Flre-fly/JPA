package jpaStudy.ex.repository.best;


import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MemberRepositoryCustom {
    //일반적으로 이런형태의 repository를 사용한다고 생각하면 된다
    private final JPAQueryFactory jpaQueryFactory;
}
