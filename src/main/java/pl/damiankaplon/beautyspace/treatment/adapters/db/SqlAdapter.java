package pl.damiankaplon.beautyspace.treatment.adapters.db;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.damiankaplon.beautyspace.treatment.domain.Treatment;
import pl.damiankaplon.beautyspace.treatment.domain.ports.outgoing.Database;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class SqlAdapter implements Database {

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
    @Transactional
    public void delete(UUID uuid) {
        repo.deleteByUuid(uuid);
    }

    @Override
    public List<Treatment> findByNameContainingIgnoreCase(String name) {
        List<TreatmentEntity> entities = repo.findByNameIgnoreCaseContaining(name);
        return toDomainsList(entities);
    }

    @Override
    public List<Treatment> findAllByNameContainingAndTypesContaining(String name, String type) {
        List<TreatmentEntity> entities = repo.findByNameIgnoreCaseContainingAndTypesContaining(name, TreatmentType.fromString(type));
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
