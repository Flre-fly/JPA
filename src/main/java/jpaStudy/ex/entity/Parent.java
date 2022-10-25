package jpaStudy.ex.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Parent {
    @Id
    @GeneratedValue
    @Column(name = "parent_id")
    private Long id;

    private String name;

    @OneToOne
    @JoinTable(name = "joinTable1", joinColumns = @JoinColumn(name = "parent_id"), inverseJoinColumns = @JoinColumn(name = "child_id"))
    private Child child;
}
