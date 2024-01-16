@RestController
@RequestMapping("/transactions")
public class TransactionController {
    private final TransactionService service;

    @Autowired
    public TransactionController(TransactionService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Transaction>> getAllByAccountId(@RequestParam Long accountId) {
        return ResponseEntity.ok(service.getTransactionsByAccountId(accountId));
    }
}
