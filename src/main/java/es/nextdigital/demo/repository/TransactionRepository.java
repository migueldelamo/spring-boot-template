public interface TransactionRepository {
    List<Transaction> findByAccountId(Long accountId);
}
