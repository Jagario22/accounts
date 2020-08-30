package com.nix.module.module3.hibernate.dao;

import com.nix.module.module3.hibernate.entity.category.IncomeCategory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;

public class IncomeCategoryDao {
    private final SessionFactory sessionFactory;
    private Session session;

    public IncomeCategoryDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void update(IncomeCategory category) {
        session = sessionFactory.getCurrentSession();
        session.update(category);
    }

    public void save(IncomeCategory category) {
        session = sessionFactory.getCurrentSession();
        session.save(category);
    }

    public IncomeCategory findByName(String name) {
        Query query;
        session = sessionFactory.getCurrentSession();
        query = session.createQuery("from IncomeCategory where name = :qName");
        query.setParameter("qName", name);
        List queryList = query.list();
        if (queryList.size() == 0){
            return null;
        }

        return (IncomeCategory) queryList.get(0);
    }
}
