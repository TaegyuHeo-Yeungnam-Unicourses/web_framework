package bank.service;

import bank.model.AccountDAO;
import bank.model.CreateReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("cservice")
public class AccountCreateService {
    private final AccountDAO dao;
    @Autowired
    public AccountCreateService(AccountDAO dao) { this.dao = dao; }

    public boolean create(CreateReq req) {
        if (dao.selectByOwner(req.getOwner()) != null) return false;
        dao.insert(req);
        return true;
    }
}
