package com.nix.module.module3.hibernate.controller;

import com.nix.module.module3.hibernate.controller.abstr.AbstractOperationController;
import com.nix.module.module3.hibernate.dao.OperationDao;
import com.nix.module.module3.hibernate.entity.Account;
import com.nix.module.module3.hibernate.entity.category.ExpenseCategory;
import com.nix.module.module3.hibernate.entity.category.IncomeCategory;
import com.nix.module.module3.hibernate.entity.operation.Expense;
import com.nix.module.module3.hibernate.entity.operation.Income;
import com.nix.module.module3.util.DateUtil;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.util.Set;

@Slf4j
public class OperationController implements AbstractOperationController {
    private final OperationDao operationDao;

    public OperationController(OperationDao operationDao) {
        this.operationDao = operationDao;
    }

    @Override
    public Income newIncomeOperation(Account account, Long amount, Set<IncomeCategory> categories) {
        Income operation = new Income();
        operation.setTimestamp(Instant.now());
        operation.setAmount(amount);
        operation.setAccount(account);
        operation.getCategories().addAll(categories);
        operationDao.saveOrUpdate(operation);
        return operation;

    }

    @Override
    public Expense newExpenseOperation(Account account, Long amount, Set<ExpenseCategory> categories) {
        Expense operation = new Expense();
        operation.setTimestamp(DateUtil.getFormattedDate(Instant.now()));
        operation.setAmount(amount);
        operation.setAccount(account);
        operation.getCategories().addAll(categories);
        operationDao.saveOrUpdate(operation);
        return operation;
    }


}
