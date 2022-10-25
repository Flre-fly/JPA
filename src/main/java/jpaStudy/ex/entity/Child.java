package jpaStudy.ex.entity;

import javax.persistence.*;

@Entity
public class Child {
    @Id
    @GeneratedValue
    @Column(name = "child_id")
    private Long id;

    @OneToOne(mappedBy = "child")
    private Parent parent;
}
