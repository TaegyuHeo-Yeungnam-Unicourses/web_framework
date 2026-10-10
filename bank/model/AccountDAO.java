package bank.model;

import java.util.Collection;
import java.util.HashMap;
import org.springframework.stereotype.Component;

@Component
public class AccountDAO {
    private final HashMap<String, Integer> ownerMap = new HashMap<>();
    private final HashMap<Integer, Account> accountMap = new HashMap<>();
    private int nextAid = 1;

    public Account selectByAid(int aid) { return accountMap.get(aid); }
    public Account selectByOwner(String owner) {
        Integer aid = ownerMap.get(owner);
        return aid == null ? null : accountMap.get(aid);
    }
    public Collection<Account> selectAll() { return accountMap.values(); }
    public void insert(CreateReq req) {
        int aid = nextAid++;
        Account account = new Account(aid, req.getOwner(), req.getName(), req.getBalance());
        ownerMap.put(req.getOwner(), aid);
        accountMap.put(aid, account);
    }
    public boolean update(Account account) {
        if (account == null || !accountMap.containsKey(account.getAid())) return false;
        Integer mappedAid = ownerMap.get(account.getOwner());
        if (mappedAid == null || mappedAid.intValue() != account.getAid()) return false;
        accountMap.put(account.getAid(), account);
        return true;
    }
}
