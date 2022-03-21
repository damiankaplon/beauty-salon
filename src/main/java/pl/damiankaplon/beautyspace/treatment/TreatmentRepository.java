package pl.damiankaplon.beautyspace.treatment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface TreatmentRepository extends JpaRepository<Treatment, Integer> {

    @Query("SELECT t FROM Treatment t WHERE t.uuid = ?1")
    Treatment findByUuid(UUID uuid);
}