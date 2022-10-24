package jpaStudy.ex.entity;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class IdenChild {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name="id")
    private Parent parent;

    private String name;

    public void setParent(Parent parent){
        this.parent = parent;
    }
}
