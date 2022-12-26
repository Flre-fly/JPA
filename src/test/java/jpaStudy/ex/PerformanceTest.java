package jpaStudy.ex;

import jpaStudy.ex.performance.HasNotRepository;
import jpaStudy.ex.performance.HasRepository;
import jpaStudy.ex.performance.MyParentRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

@SpringBootTest
public class PerformanceTest {
    @Autowired
    private MyParentRepository repository;
    @Autowired
    private HasRepository hasRepository;
    @Autowired
    private HasNotRepository hasNotRepository;

    @Test
    @Transactional
    public void 퍼포먼스(){

        long startTime = System.currentTimeMillis();
        repository.findJoinHas(1l);
        long endTime = System.currentTimeMillis();
        //61

        long startTime1 = System.currentTimeMillis();
        repository.findJoinNot(1l);
        long endTime1 = System.currentTimeMillis();
        //16

        System.out.println("-=-------------------------------------------");
        System.out.println(endTime - startTime);
        System.out.println(endTime1 - startTime1);

    }

    @Test
    @Transactional
    public void 따로따로조회vs한번에조회(){

        long startTime = System.currentTimeMillis();
        hasRepository.findByMyParentId(1l);
        hasNotRepository.findByMyParentId(1l);

        long endTime = System.currentTimeMillis();
        //61

        long startTime1 = System.currentTimeMillis();
        repository.findJoin(1l);
        long endTime1 = System.currentTimeMillis();
        //16

        System.out.println("-=-------------------------------------------");
        System.out.println(endTime - startTime);
        System.out.println(endTime1 - startTime1);

    }
}
