import java.math.BigDecimal;

public class Card {
    private Long id;
    private Account account;
    private CardType type;
    private BigDecimal withdrawalLimit; 

    public Card() {
    }

    public Card(Long id, Account account, CardType type, BigDecimal withdrawalLimit) {
        this.id = id;
        this.account = account;
        this.type = type;
        this.withdrawalLimit = withdrawalLimit;
    }

    public Long getId() {
        return id;
    }

    public Account getAccount() {
        return account;
    }

    public CardType getType() {
        return type;
    }

    public BigDecimal getWithdrawalLimit() {
        return withdrawalLimit;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public void setType(CardType type) {
        this.type = type;
    }

    public void setWithdrawalLimit(BigDecimal withdrawalLimit) {
        this.withdrawalLimit = withdrawalLimit;
    }

    public enum CardType {
        DEBIT, CREDIT
    }
}
