package jpaStudy.ex.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Group1 {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "group1_id")
    private Long id;

    private String name;

    @OneToMany(mappedBy = "group1")
    private List<Member> members = new ArrayList<>();


    public Group1(String name){
        this.name = name;
    }


}
