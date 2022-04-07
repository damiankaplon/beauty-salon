package pl.damiankaplon.beautyspace.treatment.domain.ports.incoming;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.damiankaplon.beautyspace.treatment.domain.Treatment;

import java.util.List;
import java.util.UUID;

public interface IncomingPort {
    Page<Treatment> getTreatmentsPage(Pageable pageable);
    Treatment addNewTreatment(Treatment treatment);
    List<Treatment> getAllByName(String name);
    Treatment getTreatment(UUID uuid);
    List<Treatment> getAllByType(String type);
    List<Treatment> getAllByNameAndType(String name, String type);
    List<String> getAllTypes();
    Treatment editTreatment(Treatment changes, UUID toChange);
}
