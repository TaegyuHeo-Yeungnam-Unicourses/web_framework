package bank.service;

import bank.model.Account;
import org.springframework.stereotype.Component;

@Component
public class BasicReport implements ReportTool {
    @Override
    public void print(Account account) {
        System.out.printf("계좌 ID = %d, 사용자 ID = %s, 이름 = %s, 잔액 = %d%n", account.getAid(), account.getOwner(), account.getName(), account.getBalance());
    }
}
