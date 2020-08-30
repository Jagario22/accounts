package com.nix.module.module3.hibernate.entity.category;

import com.nix.module.module3.hibernate.entity.operation.Expense;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@Entity
@Table(name = "expense_categories")
public class ExpenseCategory extends OperationCategory {

    @ManyToMany(mappedBy = "categories", fetch = FetchType.LAZY)
    private List<Expense> operations = new ArrayList<>();

    public ExpenseCategory() {
    }

    public void addOperation(Expense operation) {
        operations.add(operation);
    }
}
