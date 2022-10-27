package jpaStudy.ex.entity.sangsok;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@DiscriminatorValue("s")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Student extends Person{
    @Id
    @GeneratedValue
    private Long id;
    private Integer age;

    public Student(Integer age, String name){
        super(name);
        this.age = age;
    }
}
