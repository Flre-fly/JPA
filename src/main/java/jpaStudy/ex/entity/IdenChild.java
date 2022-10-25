package jpaStudy.ex.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@IdClass(ChildId.class)
public class IdenChild {
    @Id
    @ManyToOne
    @JoinColumn(name = "parent_id")
    public Parent parent; //이거 왜 퍼블릭이어야하지

    @Id
    private Long child_id;
    //private ChildId childId; Property of @IdClass not found in entity jpaStudy.ex.entity.IdenChild: child_id

    private String name;
}
