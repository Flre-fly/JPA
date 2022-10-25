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

    @EmbeddedId
    private ChildId childId;

    @MapsId("parentId")
    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Parent parent;

    private String name;
}
