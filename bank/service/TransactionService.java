package bank.service;

import bank.exception.AccountNotFoundException;
import bank.exception.InsufficientBalanceException;
import bank.model.Account;
import bank.model.AccountDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("tservice")
public class TransactionService {
    private final AccountDAO dao;
    @Autowired
    public TransactionService(AccountDAO dao) { this.dao = dao; }

    public int deposit(int aid, int amount) {
        Account account = dao.selectByAid(aid);
        if (account == null) throw new AccountNotFoundException();
        account.setBalance(account.getBalance() + amount);
        dao.update(account);
        return account.getBalance();
    }
    public int withdraw(int aid, int amount) {
        Account account = dao.selectByAid(aid);
        if (account == null) throw new AccountNotFoundException();
        if (account.getBalance() < amount) throw new InsufficientBalanceException();
        account.setBalance(account.getBalance() - amount);
        dao.update(account);
        return account.getBalance();
    }
}
