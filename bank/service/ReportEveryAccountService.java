package bank.service;

import bank.model.Account;
import bank.model.AccountDAO;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component("every")
@NoArgsConstructor
public class ReportEveryAccountService {
    private AccountDAO dao;
    private ReportTool printer;

    @Autowired
    public void setDao(AccountDAO dao) { this.dao = dao; }
    @Autowired
    public void setPrinter(@Qualifier("summaryReport") ReportTool printer) {
        this.printer = printer;
    }
    public void printAccountList() {
        for (Account account : dao.selectAll()) printer.print(account);
    }
}
