package jpaStudy.ex.performance;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@Table(name="has_not")
public class HasNotAnnotationEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;


    @ManyToOne
    @JoinColumn(name = "parent_id")
    private MyParent myParent;

    HasNotAnnotationEntity(String name, MyParent parent){
        this.name = name;
        this.myParent = parent;
    }
}
