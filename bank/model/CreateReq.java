package bank.model;

public class CreateReq {
    private final String owner;
    private final String name;
    private final int balance;

    public CreateReq(String owner, String name, int balance) {
        this.owner = owner;
        this.name = name;
        this.balance = balance;
    }
    public String getOwner() { return owner; }
    public String getName() { return name; }
    public int getBalance() { return balance; }
}
