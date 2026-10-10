import bank.config.Config2;
import bank.model.*;
import bank.service.*;
import bank.exception.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class BankVerification {
    private static int count;
    private static void check(boolean condition) {
        if (!condition) throw new AssertionError("check " + (count + 1));
        count++;
    }
    private static String capture(Runnable action) {
        PrintStream original = System.out;
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        try {
            System.setOut(new PrintStream(bytes, true, StandardCharsets.UTF_8));
            action.run();
        } finally { System.setOut(original); }
        return bytes.toString(StandardCharsets.UTF_8);
    }
    public static void main(String[] args) {
        try (AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(Config2.class)) {
            AccountDAO dao = ctx.getBean(AccountDAO.class);
            AccountCreateService create = ctx.getBean("cservice", AccountCreateService.class);
            TransactionService tx = ctx.getBean("tservice", TransactionService.class);
            check(dao.selectAll().isEmpty());
            String log = capture(() -> check(create.create(new CreateReq("u1", "홍길동", 1000))));
            check(log.contains("AccountCreateService.create : 호출 시간 = ") && log.contains("return 값 = true"));
            check(!create.create(new CreateReq("u1", "중복", 2000)));
            check(create.create(new CreateReq("u2", "김영희", 2000)));
            check(dao.selectAll().size() == 2 && dao.selectByOwner("u2").getAid() == 2);
            check(dao.selectByAid(1).getBalance() == 1000);
            log = capture(() -> check(tx.deposit(1, 500) == 1500));
            check(log.contains("TransactionService.deposit") && log.contains("return 값 = 1500"));
            check(tx.withdraw(1, 300) == 1200);
            log = capture(() -> {
                try { tx.withdraw(1, 1201); throw new AssertionError(); }
                catch (InsufficientBalanceException expected) { check(true); }
            });
            check(log.contains("TransactionService.withdraw") && !log.contains("return 값 ="));
            check(dao.selectByAid(1).getBalance() == 1200);
            try { tx.deposit(99, 1); throw new AssertionError(); }
            catch (AccountNotFoundException expected) { check(true); }
            try { tx.withdraw(99, 1); throw new AssertionError(); }
            catch (AccountNotFoundException expected) { check(true); }
            check(tx.withdraw(1, 1200) == 0);
            check(!dao.update(new Account(99, "u1", "오류", 9)));
            check(!dao.update(new Account(1, "u2", "오류", 9)));
            check(!dao.update(new Account(1, "missing", "오류", 9)));
            check(dao.update(new Account(1, "u1", "홍길동", 700)));
            check(dao.selectByOwner("u1").getBalance() == 700);
            ReportOneAccountService one = ctx.getBean("one", ReportOneAccountService.class);
            String report = capture(() -> one.printAccountInfo("u1"));
            check(report.contains("700") && !report.contains("홍길동") && !report.contains("u1"));
            check(capture(() -> one.printAccountInfo("none")).contains("존재하지 않는"));
            report = capture(() -> ctx.getBean("every", ReportEveryAccountService.class).printAccountList());
            check(report.lines().count() == 2 && report.contains("2000") && !report.contains("김영희"));
            report = capture(() -> ctx.getBean(BasicReport.class).print(dao.selectByAid(1)));
            check(report.contains("홍길동") && report.contains("u1") && report.contains("700"));
        }
        System.out.println("PASS: " + count + " checks");
    }
}
