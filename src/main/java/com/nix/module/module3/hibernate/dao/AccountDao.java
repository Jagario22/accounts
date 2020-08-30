package com.nix.module.module3.hibernate.dao;

import com.nix.module.module3.hibernate.entity.Account;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
@Slf4j
public class AccountDao {
    private final SessionFactory sessionFactory;
    private Session session;

    public AccountDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void update(Account account) {
        session = sessionFactory.getCurrentSession();
        session.update(account);
    }

    public Account findAccountById(Long id) {
        session =sessionFactory.getCurrentSession();
        return session.get(Account.class, id);
    }
}
