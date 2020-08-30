package com.nix.module.module3.hibernate.entity.operation;


import com.nix.module.module3.hibernate.entity.category.IncomeCategory;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


@Getter
@Setter
@Entity
@DiscriminatorValue("income")
public class Income extends Operation {

    @ManyToMany
    @JoinTable(
            name = "operation_income_categories",
            joinColumns = @JoinColumn(name = "operation_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "category_id", referencedColumnName = "id")
    )
    private final Set<IncomeCategory> categories = new HashSet<>();

    public Income() {
    }

}
