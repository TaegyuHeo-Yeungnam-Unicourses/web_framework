package bank.service;

import bank.model.Account;
import org.springframework.stereotype.Component;

@Component
public class SummaryReport implements ReportTool {
    @Override
    public void print(Account account) {
        System.out.printf("계좌 ID = %d, 잔액 = %d%n", account.getAid(), account.getBalance());
    }
}
