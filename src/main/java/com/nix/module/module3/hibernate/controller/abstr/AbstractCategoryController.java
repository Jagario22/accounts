package com.nix.module.module3.hibernate.controller.abstr;

import com.nix.module.module3.hibernate.entity.category.ExpenseCategory;
import com.nix.module.module3.hibernate.entity.category.IncomeCategory;

public interface AbstractCategoryController {
    void updateExpenseCategory(ExpenseCategory operationCategory);
    ExpenseCategory newExpenseCategory(String name);
    IncomeCategory newIncomeCategory(String name);
    void updateIncomeCategory(IncomeCategory operationCategory);
    IncomeCategory findIncomeByName(String name);
    ExpenseCategory findExpenseByName(String name);
}
