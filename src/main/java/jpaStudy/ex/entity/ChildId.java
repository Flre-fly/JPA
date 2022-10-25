package jpaStudy.ex.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@AllArgsConstructor
@Getter
@NoArgsConstructor
public class ChildId implements Serializable {
    public Long parent;
    public Long child_id;
    @Override
    public int hashCode() {
        return Objects.hash(parent, child_id);
    }

    @Override
    public boolean equals(Object obj) {
        if(obj==this) return true;
        else{
            if(obj==null || obj.getClass()!= this.getClass()) return false;
            ChildId c = (ChildId) obj;
            return parent.equals(c.parent) && child_id.equals(c.child_id);
        }
    }

}
