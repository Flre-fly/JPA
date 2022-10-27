package jpaStudy.ex.entity.sangsok;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "DTYPE")
@Getter
@NoArgsConstructor
public abstract class Person {
    @Id
    @GeneratedValue
    private Long id;

    private String name;
    public Person(String name){
        this.name = name;
    }
}
