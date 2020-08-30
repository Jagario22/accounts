package com.nix.module.module3.hibernate.controller;

import com.nix.module.module3.hibernate.controller.abstr.AbstractAccountController;
import com.nix.module.module3.hibernate.dao.AccountDao;
import com.nix.module.module3.hibernate.entity.Account;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AccountController implements AbstractAccountController {
    private final AccountDao accountDao;

    public AccountController(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    @Override
    public void updateBalance(Long amount, Long accountId) {
        Account account = accountDao.findAccountById(accountId);
        if (amount != 0 && account != null) {
            Long balance = account.getBalance() + amount;
            account.setBalance(balance);
            accountDao.update(account);
            if (amount < 0) {
                log.info("Username " + account.getUser().getFullName() +
                        " is withdrawing " + amount + " from the account with id "
                        + account.getId());
            } else {
                log.info("Username " + account.getUser().getFullName() +
                        " is putting " + amount + " in the account with id "
                        + account.getId());
            }
        }
    }

}
