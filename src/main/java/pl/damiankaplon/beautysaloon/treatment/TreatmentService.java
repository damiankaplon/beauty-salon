package pl.damiankaplon.beautysaloon.treatment;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TreatmentService {

    private final TreatmentRepoInMemory repo;

    public Treatment save(Treatment treatment) {
     return repo.save(treatment);
    }
}
