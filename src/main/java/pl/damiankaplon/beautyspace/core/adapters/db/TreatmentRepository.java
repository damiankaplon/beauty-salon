package pl.damiankaplon.beautyspace.core.adapters.db;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

interface TreatmentRepository extends JpaRepository<TreatmentEntity, Long> {
    TreatmentEntity findByUuid(UUID uuid);
    List<TreatmentEntity> findByNameIgnoreCaseContaining(String name);
    List<TreatmentEntity> findAllByTypesContains(TreatmentType type);
    List<TreatmentEntity> findByNameIgnoreCaseContainingAndTypesContaining(String name, TreatmentType type);
}
