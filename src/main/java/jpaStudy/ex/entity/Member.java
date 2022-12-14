package jpaStudy.ex.entity;


import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@NamedQuery(
        name = "Member.findByUsername",
        query = "select m from Member m where m.name = :name"
)
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MEMBER_ID")
    private Long id;

    @Column
    private String name;

    private Integer age;

    @ElementCollection
    @CollectionTable(name = "Address", joinColumns = @JoinColumn(name = "MEMBER_ID"))
    private List<Address> addressList;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "team_id")
    private Team team;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "group1_id")
    private Group1 group1;

    @Embedded
    private Address myAddress;

    public Member(String name, Team team){
        this.name = name;
        team.addMember(this);
        this.team = team;
    }
    public Member(String name, Team team, int age, Group1 group1){
        this.name = name;
        team.addMember(this);
        this.age = age;
        this.team = team;
        this.group1 = group1;
    }



}
