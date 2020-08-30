package com.nix.module.module3.hibernate.controller.abstr;

import com.nix.module.module3.hibernate.entity.Account;
import com.nix.module.module3.hibernate.entity.category.ExpenseCategory;
import com.nix.module.module3.hibernate.entity.category.IncomeCategory;
import com.nix.module.module3.hibernate.entity.operation.Expense;
import com.nix.module.module3.hibernate.entity.operation.Operation;

import java.util.Set;


public interface AbstractOperationController {
    Operation newIncomeOperation(Account accountId, Long amount, Set<IncomeCategory> operationCategories);
    Expense newExpenseOperation(Account account, Long amount, Set<ExpenseCategory> categories);
}
