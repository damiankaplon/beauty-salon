package pl.damiankaplon.beautyspace.integration;

import org.assertj.core.api.Assertions;
import org.eclipse.collections.impl.list.Interval;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import pl.damiankaplon.beautyspace.TreatmentAsserter;
import pl.damiankaplon.beautyspace.TreatmentSupplier;
import pl.damiankaplon.beautyspace.treatment.adapters.db.SqlAdapter;
import pl.damiankaplon.beautyspace.treatment.domain.Treatment;
import pl.damiankaplon.beautyspace.treatment.domain.ports.outgoing.Database;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@DataJpaTest
@Import({SqlAdapter.class})
public class DatabaseAdapterTests {

    @Autowired private Database databaseAdapter;

    static TreatmentSupplier treatmentSupplier = new TreatmentSupplier();

    @Test
    public void returnsAllTreatments() {
        //GIVEN
        List<Treatment> examples = Interval.fromTo(1, 5).stream()
                .map(i -> treatmentSupplier.randomWithName(Integer.toString(i)))
                .collect(Collectors.toList());
        examples.forEach(databaseAdapter::save);
        //WHEN
        List<Treatment> underTest = databaseAdapter.findAll();
        //THEN
        Assertions.assertThat(underTest).hasSize(5);
        Assertions.assertThat(underTest).containsAll(examples);
    }

    @Test
    public void returnsCorrectlySavedTreatment() {
        //GIVEN
        Treatment example = treatmentSupplier.random();
        //WHEN
        Treatment underTest = databaseAdapter.save(example);
        //THEN
        TreatmentAsserter.assertEquals(example, underTest);
    }

    @Test
    public void returnsCorrectTreatmentByUuid() {
        //GIVEN
        UUID uuid = UUID.randomUUID();
        Treatment example = treatmentSupplier.randomWithUuid(uuid);
        databaseAdapter.save(example);
        //WHEN
        Treatment underTest = databaseAdapter.findByUuid(uuid);
        //THEN
        TreatmentAsserter.assertEquals(example, underTest);
    }

    @Test
    public void returnsCorrectlyUpdatedTreatment() {
        //GIVEN
        Treatment example = treatmentSupplier.random();
        databaseAdapter.save(example);
        Treatment changes = treatmentSupplier.randomWithUuid(example.getUuid());
        //WHEN
        Treatment underTest = databaseAdapter.update(changes);
        //THEN
        TreatmentAsserter.assertEquals(changes, underTest);
    }

    @Test
    public void returnsTreatmentsWithNameLikeGivenIgnoringCase() {
        //GIVEN
        List.of("Chemical peeling", "Peeling", "Other PeELiNg", "not the searched one")
                .forEach(s -> databaseAdapter.save(treatmentSupplier.randomWithName(s)));
        String searchingFor = "Peeling";
        //WHEN
        List<Treatment> underTest = databaseAdapter.findByNameContainingIgnoreCase(searchingFor);
        //THEN
        Assertions.assertThat(underTest).hasSize(3);
        underTest.forEach(t -> Assertions.assertThat(t.getName()).containsIgnoringCase(searchingFor));
    }

    @Test
    public void returnsTreatmentsWithNameAndTypeLike() {
        //GIVEN
        Map<String, String> properties = Map
                .of("peeling", "Face", "Other Peeling", "Face", "example", "Body");
        properties.forEach((k, v) -> databaseAdapter.save(treatmentSupplier.randomWithNameAndType(k, v)));
        //WHEN
        List<Treatment> underTest = databaseAdapter.findAllByNameContainingAndTypesContaining("peeling", "Face");
        //THEN
        Assertions.assertThat(underTest).hasSize(2);
        underTest.forEach(t -> Assertions.assertThat(t.getName()).containsIgnoringCase("peeling"));
        underTest.forEach(t -> Assertions.assertThat(t.getTypesNames()).contains("Face"));
    }

    @Test
    public void deletesWithoutException() {
        //GIVEN
        UUID uuid = UUID.randomUUID();
        Treatment example = treatmentSupplier.randomWithUuid(uuid);
        databaseAdapter.save(example);
        //WHEN & THEN
        Assertions.assertThatNoException().isThrownBy(() -> databaseAdapter.delete(uuid));
    }

    @Test
    public void deletedDoesntExistInDbAnymore() {
        //GIVEN
        UUID uuid = UUID.randomUUID();
        Treatment example = treatmentSupplier.randomWithUuid(uuid);
        databaseAdapter.save(example);
        //WHEN
        databaseAdapter.delete(uuid);
        //THEN
        Assertions.assertThatExceptionOfType(NullPointerException.class).isThrownBy(()->databaseAdapter.findByUuid(uuid));
    }
}
