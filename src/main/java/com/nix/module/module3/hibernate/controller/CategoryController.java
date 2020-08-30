package com.nix.module.module3.hibernate.controller;

import com.nix.module.module3.hibernate.controller.abstr.AbstractCategoryController;
import com.nix.module.module3.hibernate.dao.ExpenseCategoryDao;
import com.nix.module.module3.hibernate.dao.IncomeCategoryDao;
import com.nix.module.module3.hibernate.entity.category.ExpenseCategory;
import com.nix.module.module3.hibernate.entity.category.IncomeCategory;

public class CategoryController implements AbstractCategoryController {
    private final ExpenseCategoryDao expenseCategoryDao;
    private final IncomeCategoryDao incomeCategoryDao;

    public CategoryController(ExpenseCategoryDao expenseCategoryDao, IncomeCategoryDao incomeCategoryDao) {
        this.expenseCategoryDao = expenseCategoryDao;
        this.incomeCategoryDao = incomeCategoryDao;
    }

    @Override
    public void updateExpenseCategory(ExpenseCategory operationCategory) {
        expenseCategoryDao.update(operationCategory);
    }

    @Override
    public void updateIncomeCategory(IncomeCategory operationCategory) {
        incomeCategoryDao.update(operationCategory);
    }

    @Override
    public ExpenseCategory newExpenseCategory(String name) {
        ExpenseCategory expenseCategory = new ExpenseCategory();
        expenseCategory.setName(name);
        expenseCategoryDao.save(expenseCategory);
        return expenseCategory;
    }

    @Override
    public IncomeCategory newIncomeCategory(String name) {
        IncomeCategory incomeCategory = new IncomeCategory();
        incomeCategory.setName(name);
        incomeCategoryDao.save(incomeCategory);
        return incomeCategory;
    }

    @Override
    public IncomeCategory findIncomeByName(String name) {
        IncomeCategory incomeCategory = incomeCategoryDao.findByName(name);
        return incomeCategory;
    }

    @Override
    public ExpenseCategory findExpenseByName(String name) {
        ExpenseCategory expenseCategory = expenseCategoryDao.findByName(name);
        return expenseCategory;
    }


}
