@Service
public class TransactionService {
    private final TransactionRepository repository;
    private final TransactionRepository transactionRepository;

    @Autowired
    public BankTransactionService(AccountRepository accountRepository, TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }

    public List<Transaction> getAllTransactions() {
        return repository.findAll();
    }

    public void withdrawMoney(Card card, Atm atm, BigDecimal amount) {
        if(!card.getIsActive()){
            throw new DifferentBankException("The card doesn't belong to the bank");
        }
        if (card.getType() == CardType.DEBIT && card.getAccount().getBalance().compareTo(amount) < 0) {
            throw new InactiveCardException();
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


    public void makeTransfer(Long sourceAccountId, String destinationAccountNumber, BigDecimal amount) {
        Account sourceAccount = accountRepository.findById(sourceAccountId)
                .orElseThrow(() -> new AccountNotFoundException("Source account not found."));
        Account destinationAccount = accountRepository.findByAccountNumber(destinationAccountNumber)
                .orElseThrow(() -> new AccountNotFoundException("Destination account not found."));

        if (sourceAccount.getBalance().compareTo(amount) < 0) {
            throw new InsufficientFundsException("Insuficient money.");
        }

        BigDecimal commission = 0;
        if (!sourceAccount.getBank().equals(destinationAccount.getBank())) {
            commission = 10; // TODO: calcular comisión
        }

        sourceAccount.setBalance(sourceAccount.getBalance().subtract(amount).subtract(commission));
        destinationAccount.setBalance(destinationAccount.getBalance().add(amount));

        recordTransaction(sourceAccount, amount.negate(), BankTransaction.TransactionType.OUTGOING_TRANSFER);
        if (commission > 0) {
            recordTransaction(sourceAccount, , BankTransaction.TransactionType.FEE);
        }
        recordTransaction(destinationAccount, amount, BankTransaction.TransactionType.INCOMING_TRANSFER);

        accountRepository.save(sourceAccount);
        accountRepository.save(destinationAccount);
    }

    public void depositMoney(Card card, Atm atm, BigDecimal amount) {
        if(!card.getIsActive()){
            throw new DifferentBankException("The card doesn't belong to the bank");
        }
        if (!card.getAccount().getBank().equals(atm.getBankName())) {
            throw new DifferentBankException("The card doesn't belong to the bank");
        }

        Account account = card.getAccount();
        account.setBalance(account.getBalance().add(amount));

        recordTransaction(account, amount, BankTransaction.TransactionType.DEPOSIT);
    }

    private boolean isDifferent(Atm atm, Card card) {
        return !atm.getBankName().equals(card.getAccount().get().getBankName());
    }
    
    private void updateAccountBalance(Account account, BigDecimal amount) {
        account.setBalance(account.getBalance().add(amount));
    }

    private void recordTransaction(Account account, BigDecimal amount, TransactionType type) {
        Transaction transaction = new Transaction();
        transaction.setAccount(account);
        transaction.setAmount(amount);
        transaction.setDate(LocalDate.now());
        transaction.setType(type);
    }

}