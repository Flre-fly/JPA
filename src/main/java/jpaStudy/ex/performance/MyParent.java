package jpaStudy.ex.performance;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

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

    @Transient
    private List<HasAnnotationEntity> hasList;
    @Transient
    private List<HasNotAnnotationEntity> hasNotList;

    MyParent(String name){
        this.name = name;
    }
}
