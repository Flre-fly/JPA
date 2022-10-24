package jpaStudy.ex.entity;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class DeIdenChild {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumns({@JoinColumn(name = "id1"), @JoinColumn(name="id2") })
    private Parent parent;

    private String name;

    public void setParent(Parent parent){
        this.parent = parent;
    }
}
