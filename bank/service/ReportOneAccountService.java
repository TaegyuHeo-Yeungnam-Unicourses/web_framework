package bank.service;

import bank.model.Account;
import bank.model.AccountDAO;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component("one")
@NoArgsConstructor
public class ReportOneAccountService {
    private AccountDAO dao;
    private ReportTool printer;

    @Autowired
    public void setDao(AccountDAO dao) { this.dao = dao; }
    @Autowired
    public void setPrinter(@Qualifier("summaryReport") ReportTool printer) {
        this.printer = printer;
    }
    public void printAccountInfo(String owner) {
        Account account = dao.selectByOwner(owner);
        if (account == null) {
            System.out.println("존재하지 않는 사용자 id입니다.");
            return;
        }
        printer.print(account);
    }
}
