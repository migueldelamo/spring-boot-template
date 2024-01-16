@RestController
@RequestMapping("/transactions")
public class TransactionController {
    private final TransactionService service;
    private final AccountService accountService;

    @Autowired
    public TransactionController(TransactionService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Transaction>> getAllByAccountId(@RequestParam Long accountId) {
        return ResponseEntity.ok(service.getTransactionsByAccountId(accountId));
    }

    @PostMapping("/transfer")
    public ResponseEntity<?> transfer(@RequestParam Long sourceAccountId, @RequestParam String destinationAccountNumber, @RequestParam BigDecimal amount) {
        try {
            Account sourceAccount = accountService.getAccountById(sourceAccountId);
            transactionService.makeTransfer(sourceAccount, destinationAccountNumber, amount);
            return ResponseEntity.ok("Successful transfer");
        } catch (AccountNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Account not found");
        } catch (InsufficientFundsException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Insufficient money");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
        }
    }
}
