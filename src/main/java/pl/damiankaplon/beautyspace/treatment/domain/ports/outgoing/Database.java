package pl.damiankaplon.beautyspace.treatment.domain.ports.outgoing;

import pl.damiankaplon.beautyspace.treatment.domain.Treatment;

import java.util.List;
import java.util.UUID;

public interface Database {
    Treatment findByUuid(UUID uuid);
    List<Treatment> findAllByNameContaining(String name);
    List<Treatment> findAllByTypesContains(String typeName);
    List<Treatment> findAllByNameContainingAndTypesContaining(String name, String type);
    List<Treatment> findAll();
    Treatment save(Treatment treatment);
    Treatment update(Treatment treatment);
}
