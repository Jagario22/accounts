package com.nix.module.module3.hibernate.entity.operation;


import com.nix.module.module3.hibernate.entity.category.ExpenseCategory;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@DiscriminatorValue("expense")
public class Expense extends Operation {

    @ManyToMany
    @JoinTable(
            name = "operations_expense_categories",
            joinColumns = @JoinColumn(name = "operation_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "category_id", referencedColumnName = "id")
    )
    private final Set<ExpenseCategory> categories = new HashSet<>();

    public Set<ExpenseCategory> getCategories() {
        return categories;
    }



}
