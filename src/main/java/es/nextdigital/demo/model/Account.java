public class Account {
    private Long id;
    private String accountNumber;
    private BigDecimal balance;
    private BigDecimal creditLimit;

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

}
