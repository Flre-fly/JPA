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

    private Long parentId;

    @Transient
    private MyParent parent;

    HasNotAnnotationEntity(String name, Long parentId){
        this.name = name;
        this.parentId = parentId;
    }
}
