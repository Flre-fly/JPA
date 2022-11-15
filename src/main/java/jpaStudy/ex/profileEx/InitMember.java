package jpaStudy.ex.profileEx;

import jpaStudy.ex.entity.Member;
import jpaStudy.ex.entity.Team;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Profile("local")
@Component
@RequiredArgsConstructor
public class InitMember {

    private final InitMemberService initMemberService;

    @PostConstruct
    public void init() {
        initMemberService.init();
    }

    @Component
    static class InitMemberService {
        @PersistenceContext
        private EntityManager em;

        @Transactional
        public void init() {
            Team teamA = new Team("TeamA");
            Team teamB = new Team("TeamB");
            if(em.find(Team.class, 1l)==null){
                em.persist(teamA);
                em.persist(teamB);

                for (int i = 0; i < 100; i++) {
                    Team selectedTeam = i % 2 == 0 ? teamA : teamB;
                    em.persist(new Member("member" + i, selectedTeam, i));
                }
            }

        }
    }
}