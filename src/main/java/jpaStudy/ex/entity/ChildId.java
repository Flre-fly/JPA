package jpaStudy.ex.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import java.io.Serializable;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Embeddable
public class ChildId  implements Serializable {
    private Long childId;
    private Long parentId;
    @Override
    public int hashCode() {
        return Objects.hash(childId, parentId);
    }

    @Override
    public boolean equals(Object obj) {
        if(obj==this) return true;
        else{
            if(obj==null || obj.getClass()!= this.getClass()) return false;
            ChildId c = (ChildId) obj;
            return childId.equals(c.childId) && parentId.equals(c.parentId);
        }
    }
}
