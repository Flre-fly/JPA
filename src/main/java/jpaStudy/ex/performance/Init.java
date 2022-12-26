package jpaStudy.ex.performance;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
@RequiredArgsConstructor
public class Init {
    private final MyParentRepository myParentRepository;
    private final HasNotRepository hasNotRepository;
    private final HasRepository hasRepository;

    @EventListener(ApplicationReadyEvent.class)
    @Transactional
    public void initDB(){
        initTest();
    }

    public void initTest() {
        MyParent parent = new MyParent("parent1");
        myParentRepository.save(parent);
        for(int i=0;i<1000;i++){

            if(i%2==0){
                HasAnnotationEntity entity = new HasAnnotationEntity("HAS " + i, parent);
                hasRepository.save(entity);
            }
            else{
                HasNotAnnotationEntity entity = new HasNotAnnotationEntity("NOT" + i, parent);
                hasNotRepository.save(entity);
            }
        }
    }
}
