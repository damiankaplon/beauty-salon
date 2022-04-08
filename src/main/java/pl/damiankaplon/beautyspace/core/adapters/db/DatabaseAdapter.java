package pl.damiankaplon.beautyspace.core.adapters.db;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import pl.damiankaplon.beautyspace.core.domain.Treatment;
import pl.damiankaplon.beautyspace.core.domain.ports.outgoing.Database;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class DatabaseAdapter implements Database {

    private final TreatmentRepository repo;

    @Override
    public Treatment findByUuid(UUID uuid) {
        TreatmentEntity entity = repo.findByUuid(uuid);
        return TreatmentEntity.to(entity);
    }

    @Override
    public Treatment save(Treatment treatment) {
        TreatmentEntity entity = TreatmentEntity.of(treatment);
        return TreatmentEntity.to(repo.save(entity));
    }

    @Override
    public Treatment update(Treatment treatment) {
        TreatmentEntity entity = repo.findByUuid(treatment.getUuid());
        TreatmentEntity toSave = TreatmentEntity.of(treatment);
        toSave.setId(entity.getId());
        return TreatmentEntity.to(repo.save(toSave));
    }

    @Override
    public List<Treatment> findAllByNameContaining(String name) {
        List<TreatmentEntity> entities = repo.findAllByNameContaining(name);
        return toDomainsList(entities);
    }

    @Override
    public List<Treatment> findAllByNameContainingAndTypesContaining(String name, String type) {
        List<TreatmentEntity> entities = repo.findAllByNameContainingAndTypesContaining(name, TreatmentType.fromString(type));
        return toDomainsList(entities);

    }

    @Override
    public List<Treatment> findAll() {
        List<TreatmentEntity> entities = repo.findAll();
        return toDomainsList(entities);
    }

    private List<Treatment> toDomainsList(List<TreatmentEntity> treatments) {
        return treatments.stream()
                .map(TreatmentEntity::to)
                .collect(Collectors.toList());
    }
}
