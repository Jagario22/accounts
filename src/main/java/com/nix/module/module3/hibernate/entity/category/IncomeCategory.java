package com.nix.module.module3.hibernate.entity.category;

import com.nix.module.module3.hibernate.entity.operation.Income;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "income_categories")
public class IncomeCategory extends OperationCategory {

    @ManyToMany(mappedBy = "categories")
    private List<Income> operations = new ArrayList<>();

    public IncomeCategory() {
    }
}
