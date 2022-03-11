package pl.damiankaplon.beautysaloon.treatment;

import lombok.Getter;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Getter
@Repository
class TreatmentRepoInMemory {

    private List<Treatment> treatments = new ArrayList<>();


    public Treatment save(Treatment treatment) {
        this.treatments.add(treatment);
        return treatment;
    }
}
