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

    private String name;
}
