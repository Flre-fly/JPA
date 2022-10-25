package jpaStudy.ex.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Setter
@Getter
public class DeIdenChild {
    @Id
    private Long id;

    @JoinColumns({
            @JoinColumn(name = "id1"),
            @JoinColumn(name = "id2")
    }
    )
    @ManyToOne
    private Parent parent;


    private String name;
}
