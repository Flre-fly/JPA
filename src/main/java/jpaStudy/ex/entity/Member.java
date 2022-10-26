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
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MEMBER_ID")
    private Long id;

    @Column
    private String name;

    @ElementCollection
    @CollectionTable(name = "Address", joinColumns = @JoinColumn(name = "MEMBER_ID"))
    private List<Address> addressList;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "team_id")
    private Team team;

    @ManyToOne
    @JoinColumn(name = "group1_id")
    private Group1 group1;




}
