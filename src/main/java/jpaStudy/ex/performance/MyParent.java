package jpaStudy.ex.performance;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name="my_parent")
@NoArgsConstructor
public class MyParent {
    @Id
    @Column(name = "parent_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "myParent")//인스턴스 이름넣어야함 테이블명이 아니라
    private List<HasAnnotationEntity> hasList = new ArrayList<>();
    @OneToMany(mappedBy = "myParent")
    private List<HasNotAnnotationEntity> hasNotList = new ArrayList<>();

    MyParent(String name){
        this.name = name;
    }
}
