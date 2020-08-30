package com.nix.module.module3;

import com.nix.module.module3.hibernate.OperationMaker;
import com.nix.module.module3.hibernate.controller.AccountController;
import com.nix.module.module3.hibernate.controller.CategoryController;
import com.nix.module.module3.hibernate.controller.OperationController;
import com.nix.module.module3.hibernate.dao.AccountDao;
import com.nix.module.module3.hibernate.dao.ExpenseCategoryDao;
import com.nix.module.module3.hibernate.dao.IncomeCategoryDao;
import com.nix.module.module3.hibernate.dao.OperationDao;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;


@Slf4j
public class JPAApplication {

    public static void main(String[] args) {
        final Long id = Long.valueOf(args[0]);
        final String username = args[1];
        final String password = args[2];

        Configuration configuration = new Configuration()
                .setProperty("hibernate.connection.username", username)
                .setProperty("hibernate.connection.password", password)
                .configure();

        try (SessionFactory sessionFactory = configuration.buildSessionFactory();
             Session session = sessionFactory.openSession()) {

            final OperationDao operationDao = new OperationDao(sessionFactory);
            final OperationController operationController = new OperationController(operationDao);

            final IncomeCategoryDao incomeCategoryDao = new IncomeCategoryDao(sessionFactory);
            final ExpenseCategoryDao expenseCategoryDao = new ExpenseCategoryDao(sessionFactory);
            final CategoryController categoryController = new CategoryController(expenseCategoryDao, incomeCategoryDao);

            final AccountDao accountDao = new AccountDao(sessionFactory);
            final AccountController accountController = new AccountController(accountDao);

            final OperationMaker operationMaker = new OperationMaker(sessionFactory, operationController, categoryController, accountController);
            
            operationMaker.makeOperation(id);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
