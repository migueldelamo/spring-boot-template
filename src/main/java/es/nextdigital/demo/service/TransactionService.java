@Service
public class TransactionService {
    private final TransactionRepository repository;

    @Autowired
    public TransactionService(TransactionRepository repository) {
        this.repository = repository;
    }

    public List<Transaction> getAllTransactions() {
        return repository.findAll();
    }

    public void withdrawMoney(Card card, Atm atm, BigDecimal amount) {
        if (card.getType() == CardType.DEBIT && card.getAccount().getBalance().compareTo(amount) < 0) {
            throw new InsufficientFundsException();
        } else if (card.getType() == CardType.CREDIT && card.getAccount().getCreditLimit().add(card.getAccount().getBalance()).compareTo(amount) < 0) {
            throw new CreditLimitExceededException();
        }

        if (card.getWithdrawalLimit().compareTo(amount) < 0) {
            throw new WithdrawalLimitExceededException();
        }

        BigDecimal fee = (isDifferent(atm, card) ? atm.getWithdrawalFee() : BigDecimal.ZERO);
        BigDecimal totalAmount = amount.add(fee);

        updateAccountBalance(card.getAccount(), totalAmount.negate());
        recordTransaction(card.getAccount(), totalAmount, TransactionType.WITHDRAWAL)

        if (fee.compareTo(BigDecimal.ZERO) > 0) {
            recordTransaction(card.getAccount(), fee, TransactionType.FEE);
        }
    }

    private boolean isDifferent(Atm atm, Card card) {
        return !atm.getBankName().equals(card.getAccount().get().getBankName());
    }
    
    private void updateAccountBalance(Account account, BigDecimal amount) {
        account.setBalance(account.getBalance().add(amount));
    }
    
    public void depositMoney(Card card, Atm atm, BigDecimal amount) {
        if (!card.getAccount().getBank().equals(atm.getBankName())) {
            throw new DifferentBankException("The card doesn't belong to the bank");
        }

        BankAccount account = card.getAccount();
        account.setBalance(account.getBalance().add(amount));

        recordTransaction(account, amount, BankTransaction.TransactionType.DEPOSIT);
    }

    private void recordTransaction(Account account, BigDecimal amount, TransactionType type) {
        Transaction transaction = new Transaction();
        transaction.setAccount(account);
        transaction.setAmount(amount);
        transaction.setDate(LocalDate.now());
        transaction.setType(type);
    }

    

}