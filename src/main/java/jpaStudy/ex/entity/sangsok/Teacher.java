package jpaStudy.ex.entity.sangsok;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@DiscriminatorValue("t")
@NoArgsConstructor
@Getter
public class Teacher extends Person{
    @Id
    @GeneratedValue
    private Long id;

    private String level;

    public Teacher(String level, String name){
        super(name);
        this.level = level;
    }
}
