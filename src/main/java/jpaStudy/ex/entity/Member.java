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

    @Column(unique = true)
    private String name;

    @ElementCollection
    @CollectionTable(name = "Address", joinColumns = @JoinColumn(name = "MEMBER_ID"))
    private List<Address> addressList;





}
