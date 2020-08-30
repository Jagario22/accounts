package com.nix.module.module3.hibernate;

import com.nix.module.module3.hibernate.controller.AccountController;
import com.nix.module.module3.hibernate.controller.CategoryController;
import com.nix.module.module3.hibernate.controller.OperationController;
import com.nix.module.module3.hibernate.entity.Account;
import com.nix.module.module3.hibernate.entity.category.ExpenseCategory;
import com.nix.module.module3.hibernate.entity.category.IncomeCategory;
import com.nix.module.module3.hibernate.entity.operation.Expense;
import com.nix.module.module3.hibernate.entity.operation.Income;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.model.naming.IllegalIdentifierException;

import java.util.*;

@Slf4j
public class OperationMaker {
    private final SessionFactory sessionFactory;
    private final OperationController operationController;
    private final CategoryController categoryController;
    private final AccountController accountController;
    private Session session;
    private String[] categories;
    private Account account;
    private Long amount;
    private Long userId;


    public OperationMaker(SessionFactory sessionFactory, OperationController operationController,
                          CategoryController categoryController, AccountController accountController) {
        this.sessionFactory = sessionFactory;
        this.operationController = operationController;
        this.categoryController = categoryController;
        this.accountController = accountController;
        ;
    }

    public void makeOperation(Long userId) throws Exception {
        session = sessionFactory.getCurrentSession();
        this.userId = userId;

        session.beginTransaction();

        requestData();
        if (amount > 0) {
            makeIncomeOperation();
            log.info("Username " + account.getUser().getFullName() + " with account id " + account.getId() +
                    " made income operation with amount " + amount + " and categories " + Arrays.toString(categories));
        } else {
            makeExpenseOperation();
            log.info("Username " + account.getUser().getFullName() + " with account id " + account.getId() +
                    " made expense operation with amount " + amount + " and categories " + Arrays.toString(categories));
        }

        session.getTransaction().commit();
    }

    private void requestData() {
        Scanner in = new Scanner(System.in);

        System.out.println("Введите номер аккаунта: ");
        Long accId = in.nextLong();
        account = session.get(Account.class, accId);

        if (!isAccountExist()) {
            throw new IllegalIdentifierException("Account with id " + accId + " was not found");
        }

        System.out.println("Введите сумму: ");
        amount = in.nextLong();

        if (!checkAmount())
            throw new RuntimeException("Amount is not valid");

        System.out.println("Ввведите имена категорий (разделение запятой): ");
        String categoriesString = in.next();

        categories = categoriesString.trim().split(",");
        if (!checkCategories())
            throw new RuntimeException("Categories aren't valid. Operation must has one or more categories");

    }

    private void makeIncomeOperation() {
        Set<IncomeCategory> incomeCategories = new HashSet<>();
        IncomeCategory incomeCategory;

        for (String name : categories) {

            incomeCategory = categoryController.findIncomeByName(name);

            if (incomeCategory == null) {
                incomeCategory = categoryController.newIncomeCategory(name);
            }
            incomeCategories.add(incomeCategory);

        }
        accountController.updateBalance(amount, account.getId());
        Income operation = operationController.newIncomeOperation(account, amount, incomeCategories);

        for (IncomeCategory category : incomeCategories) {
            category.getOperations().add(operation);
            categoryController.updateIncomeCategory(category);
        }
    }

    private void makeExpenseOperation() {
        Set<ExpenseCategory> expenseCategories = new HashSet<>();
        ExpenseCategory expenseCategory;

        for (String name : categories) {

            expenseCategory = categoryController.findExpenseByName(name);

            if (expenseCategory == null) {
                expenseCategory = categoryController.newExpenseCategory(name);
            }
            expenseCategories.add(expenseCategory);

        }
        accountController.updateBalance(amount, account.getId());
        Expense operation = operationController.newExpenseOperation(account, amount, expenseCategories);

        for (ExpenseCategory category : expenseCategories) {
            category.getOperations().add(operation);
            categoryController.updateExpenseCategory(category);
        }

    }

    private boolean isAccountExist() {
        return account.getUser().getId().equals(userId);
    }

    private boolean checkAmount() {
        return amount != 0 && account.getBalance() >= amount;
    }

    private boolean checkCategories() {
        if (categories.length == 0)
            return false;

        int i = 0;
        for (String val : categories) {
            categories[i++] = val.trim();
        }

        return true;
    }
}
