package bank;

import bank.config.Config2;
import bank.exception.AccountNotFoundException;
import bank.exception.InsufficientBalanceException;
import bank.model.CreateReq;
import bank.service.AccountCreateService;
import bank.service.ReportEveryAccountService;
import bank.service.ReportOneAccountService;
import bank.service.TransactionService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Scanner;

public class TestDriver {

    public static void main(String[] args) {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(Config2.class);
        Scanner sc = new Scanner(System.in);
        int result;

        while (true) {
            System.out.print("\n명령어 인자들... 또는 help : ");
            String[] line = sc.nextLine().split(" ");
            if (line[0].equals("exit") || line[0].equals("quit"))
                break;
            else if (line[0].equals("help"))
                print_help();
            else if (line[0].equals("create")) {
                AccountCreateService acs = (AccountCreateService)ctx.getBean("cservice");
                CreateReq req = new CreateReq(line[1], line[2], Integer.parseInt(line[3]));
                if (acs.create(req))
                    System.out.println("정상적으로 생성되었습니다.");
                else
                    System.out.println("오류가 발생하였습니다: 중복된 계좌가 존재합니다.");
            }
            else if (line[0].equals("deposit")) {
                TransactionService tcs = (TransactionService)ctx.getBean("tservice");
                try {
                    result = tcs.deposit(Integer.parseInt(line[1]), Integer.parseInt(line[2]));
                    System.out.println("정상적으로 입금되었습니다. 잔액 = " + result);
                } catch (AccountNotFoundException e) {
                    System.out.println("존재하지 않는 계좌 id입니다.");
                }
            }
            else if (line[0].equals("withdraw")) {
                TransactionService tcs = (TransactionService)ctx.getBean("tservice");
                try {
                    result = tcs.withdraw(Integer.parseInt(line[1]), Integer.parseInt(line[2]));
                    System.out.println("정상적으로 출금되었습니다. 잔액 = " + result);
                } catch (AccountNotFoundException e) {
                    System.out.println("존재하지 않는 계좌 id입니다.");
                } catch (InsufficientBalanceException e) {
                    System.out.println("잔고가 부족합니다.");
                }
            }
            else if (line[0].equals("report")) {
                ReportOneAccountService ras = (ReportOneAccountService)ctx.getBean("one");
                ras.printAccountInfo(line[1]);
            }
            else if (line[0].equals("report-all")) {
                ReportEveryAccountService res = (ReportEveryAccountService)ctx.getBean("every");
                res.printAccountList();
            }
            else {
                System.out.println(line[0] + " : 잘못된 명령어 입니다.");
                print_help();
            }
        }
        sc.close();
    }

    public static void print_help() {
        System.out.println("\tcreate 사용자id 이름 잔고");
        System.out.println("\tdeposit 계좌id 입금액");
        System.out.println("\twithdraw 계좌id 출금액");
        System.out.println("\treport 사용자id");
        System.out.println("\treport-all");
    }
}
