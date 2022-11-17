package jpaStudy.ex.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Users {
    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private List<UserDetails> details = new ArrayList<>();

    public void addDetail(UserDetails detail){
        details.add(detail);
    }
    public Users(String name){
        this.name = name;
    }

    public Users() {

    }
}
