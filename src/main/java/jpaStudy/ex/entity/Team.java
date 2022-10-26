package jpaStudy.ex.entity;

import lombok.*;
import org.hibernate.collection.internal.PersistentBag;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "team_id")
    private Long id;

    @Column(unique = true)
    private String name;

    @OneToMany(mappedBy = "team", fetch = FetchType.EAGER)
    private List<Member> members = new ArrayList<>();

    public void addMember(Member member){
        this.members.add(member);
        member.setTeam(this);
    }

    @OneToMany(mappedBy = "team")
    private List<Food> foods = new ArrayList<>();

    public void addFood(Food food){
        this.foods.add(food);
        food.setTeam(this);
    }



}
