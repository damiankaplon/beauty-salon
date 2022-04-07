package pl.damiankaplon.beautyspace;

import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import pl.damiankaplon.beautyspace.treatment.adapters.db.DatabaseAdapter;
import pl.damiankaplon.beautyspace.treatment.domain.Treatment;
import pl.damiankaplon.beautyspace.treatment.domain.TreatmentService;

import java.time.LocalTime;
import java.util.Set;
import java.util.UUID;

import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class EditTreatmentTests {

    @Mock
    DatabaseAdapter databaseAdapter;

    @InjectMocks
    TreatmentService service;

    TreatmentSupplier treatmentSupplier = new TreatmentSupplier();

    @Test
    public void editFullyTreatmentTest() {
        //GIVEN
        Treatment changes = Treatment.builder()
                .name("changed")
                .shortDescription("short desc changed")
                .fullDescription("full desc changed")
                .images(Set.of("changed.jpg"))
                .aproxTime(LocalTime.parse("99:99"))
                .types(Set.of("Full body"))
                .priceRange(999f, 9999f)
                .build();

        UUID toChange = UUID.fromString("123e4567-e89b-42d3-a456-556642440000");

        when(databaseAdapter.findByUuid(any(UUID.class)))
                .thenReturn(treatmentSupplier.testTreatment());

        when(databaseAdapter.save(any(Treatment.class)))
                .then(returnsFirstArg());

        //WHEN
        Treatment changedTreatment = service.editTreatment(changes, toChange);
        //THEN
        Assertions.assertEquals(changedTreatment.getUuid(), toChange);
        Assertions.assertEquals(changedTreatment.getName(), changes.getName());
        Assertions.assertEquals(changedTreatment.getName(), changes.getName());
        Assertions.assertEquals(changedTreatment.getShortDescription(), changes.getShortDescription());
        Assertions.assertEquals(changedTreatment.getFullDescription(), changes.getFullDescription());
        Assertions.assertEquals(changedTreatment.getAproxTime().toString(), changes.getAproxTime().toString());
        Assertions.assertEquals(changedTreatment.getImagesSrcs(), changes.getImagesSrcs());
        Assertions.assertEquals(changedTreatment.getTypesNames(), changes.getTypesNames());
        Assertions.assertEquals(changedTreatment.getMinPrice(), changes.getMinPrice());
        Assertions.assertEquals(changedTreatment.getMaxPrice(), changes.getMaxPrice());
    }
}
