package jpaStudy.ex.entity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@SecondaryTable(name = "board_detail", pkJoinColumns = @PrimaryKeyJoinColumn(name= "board_detail_id"))
public class Board {
    @Id
    @Column(name = "board_id")
    private Long id;

    private String title;

    @Column(table = "board_detail")
    private String content;
}
