package jpaStudy.ex.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class IdenChild {

    @Id
    private Long child_id;
    //private ChildId childId; Property of @IdClass not found in entity jpaStudy.ex.entity.IdenChild: child_id

    private String name;
}
