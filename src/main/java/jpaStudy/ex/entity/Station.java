package jpaStudy.ex.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class Station {
    private String name;
    public Station(String name){
        this.name = name;
    }
}
