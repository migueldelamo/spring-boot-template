import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AtmService {
    private final AtmRepository atmRepository; 

    @Autowired
    public AtmService(AtmRepository atmRepository) {
        this.atmRepository = atmRepository;
    }

    public Atm getAtmById(Long atmId) {
        return atmRepository.findById(atmId)
                .orElseThrow(() -> new AtmNotFoundException("Atm with ID: " + atmId + " not found"));
    }

    
}
