package com.nix.module.module3.hibernate.entity.category;

import com.nix.module.module3.hibernate.entity.AbstractEntity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;

@Getter
@Setter
@MappedSuperclass
public abstract class OperationCategory extends AbstractEntity {

    @NaturalId
    @Column(nullable = false)
    private String name;

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        OperationCategory that = (OperationCategory) o;
        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    public OperationCategory() {
    }
}
