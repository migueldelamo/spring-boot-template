import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/atm")
public class AtmController {
    private final TransactionService transactionService;
    private final CardService cardService; 
    private final AtmService atmService; 

    @Autowired
    public AtmController(TransactionService transactionService, CardService cardService, AtmService atmService) {
        this.transactionService = transactionService;
        this.cardService = cardService;
        this.atmService = atmService;
    }

    @PostMapping("/withdraw")
    public ResponseEntity<?> withdrawMoney(@RequestParam Long cardId, @RequestParam Long atmId, @RequestParam BigDecimal amount) {
        try {
            Card card = cardService.getCardById(cardId);
            Atm atm = atmService.getAtmById(atmId);
            transactionService.withdrawMoney(card, atm, amount);
            return ResponseEntity.ok().body("Withdray successful.");
        } catch (InsufficientFundsException e) {
            return ResponseEntity.badRequest().body("Insufficient money.");
        } catch (CreditLimitExceededException e) {
            return ResponseEntity.badRequest().body("Credit limit exceeded.");
        } catch (WithdrawalLimitExceededException e) {
            return ResponseEntity.badRequest().body("Withdraw limit exceeded.");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("An error occurred while withdrawing the money.");
        }
    }
}
