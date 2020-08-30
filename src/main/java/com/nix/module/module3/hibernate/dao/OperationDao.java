package com.nix.module.module3.hibernate.dao;

import com.nix.module.module3.hibernate.entity.operation.Operation;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class OperationDao {
    private final SessionFactory sessionFactory;
    private Session session;

    public OperationDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void saveOrUpdate(Operation operation) {
        session = sessionFactory.getCurrentSession();
        session.save(operation);
    }
}
