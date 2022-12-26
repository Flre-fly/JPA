package jpaStudy.ex.performance;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@Table(name="my_parent")
@NoArgsConstructor
public class MyParent {
    @Id
    @Column(name = "parent_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    MyParent(String name){
        this.name = name;
    }
}
