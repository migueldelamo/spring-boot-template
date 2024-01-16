public class Atm {
    private Long id;
    private String bankName; 
    private BigDecimal withdrawalFee; 

    public Atm() {
    }

    public Atm(Long id, String bankName, BigDecimal withdrawalFee) {
        this.id = id;
        this.bankName = bankName;
        this.withdrawalFee = withdrawalFee;
    }

    public Long getId() {
        return id;
    }

    public String getBankName() {
        return bankName;
    }

    public BigDecimal getWithdrawalFee() {
        return withdrawalFee;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public void setWithdrawalFee(BigDecimal withdrawalFee) {
        this.withdrawalFee = withdrawalFee;
    }

}
