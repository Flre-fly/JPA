package jpaStudy.ex.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    public UserDetails(String name){
        this.name = name;
    }


    public UserDetails() {

    }
}
