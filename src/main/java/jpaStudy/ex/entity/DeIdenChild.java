package jpaStudy.ex.entity;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class DeIdenChild {
    @Id
    @GeneratedValue
    private Long id;


    private String name;
}
