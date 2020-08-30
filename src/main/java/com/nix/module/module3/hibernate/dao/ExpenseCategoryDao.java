package com.nix.module.module3.hibernate.dao;

import com.nix.module.module3.hibernate.entity.category.ExpenseCategory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;


public class ExpenseCategoryDao {
    private final SessionFactory sessionFactory;
    private Session session;

    public ExpenseCategoryDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void update(ExpenseCategory category) {
        session = sessionFactory.getCurrentSession();
        session.update(category);
    }

    public void save(ExpenseCategory category) {
        session = sessionFactory.getCurrentSession();
        session.save(category);
    }

    public ExpenseCategory findByName(String name) {
        session = sessionFactory.getCurrentSession();
        Query query;


        query = session.createQuery("from ExpenseCategory where name = :qName");
        query.setParameter("qName", name);

        List queryList = query.list();

        if (queryList.size() == 0){
            return null;
        }

        return (ExpenseCategory) queryList.get(0);
    }
}
