public class Account {
    private Long id;
    private String accountNumber;
    private BigDecimal balance;
    private BigDecimal creditLimit;
    private Bank bank;

    public Account() {}

    public Account(Long id, String accountNumber, BigDecimal balance, BigDecimal creditLimit) {
        this.id = id;
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.creditLimit = creditLimit;
    }

    public Long getId() {
        return id;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public BigDecimal getCreditLimit() {
        return creditLimit;
    }

    public Bank getBank() {
        return bank;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public void setCreditLimit(BigDecimal creditLimit) {
        this.creditLimit = creditLimit;
    }

    public void setBank(Bank bank) {
        this.bank = bank;
    }

}
