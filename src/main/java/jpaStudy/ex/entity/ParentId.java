package jpaStudy.ex.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ParentId implements Serializable {
    private Long id1;
    private Long id2;
    @Override
    public int hashCode() {
        return Objects.hash(id1, id2);
    }

    @Override
    public boolean equals(Object obj) {
        if(obj==this) return true;
        else{
            if(obj==null || obj.getClass()!= this.getClass()) return false;
            ParentId p = (ParentId) obj;
            return id1.equals(p.id1) && id2.equals(p.id2);
        }
    }
}
