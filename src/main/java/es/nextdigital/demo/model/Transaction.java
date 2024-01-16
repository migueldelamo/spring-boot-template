public class Transaction {
    private Long id;
    private LocalDate date;
    private BigDecimal amount;
    private TransactionType type;

    public Transaction(Long id, LocalDate date, BigDecimal amount, TransactionType type, Account account) {
        this.id = id;
        this.date = date;
        this.amount = amount;
        this.type = type;
        this.account = account;
    }

    public Long getId() {
        return id;
    }

    public LocalDate getDate() {
        return date;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public TransactionType getType() {
        return type;
    }

    public Account getAccount() {
        return account;
    }

    // Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    // Enum para los tipos de transacciones
    public enum TransactionType {
        DEPOSIT, WITHDRAWAL, FEE, INCOMING_TRANSFER, OUTGOING_TRANSFER
    }


}
