package bank.model;

public class Account {
    private final int aid;
    private final String owner;
    private final String name;
    private int balance;

    public Account(int aid, String owner, String name, int balance) {
        this.aid = aid;
        this.owner = owner;
        this.name = name;
        this.balance = balance;
    }
    public int getAid() { return aid; }
    public String getOwner() { return owner; }
    public String getName() { return name; }
    public int getBalance() { return balance; }
    public void setBalance(int balance) { this.balance = balance; }
}
