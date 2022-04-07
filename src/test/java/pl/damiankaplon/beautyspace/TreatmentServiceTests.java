package pl.damiankaplon.beautyspace;

import org.eclipse.collections.impl.list.Interval;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import pl.damiankaplon.beautyspace.treatment.adapters.db.DatabaseAdapter;
import pl.damiankaplon.beautyspace.treatment.domain.Treatment;
import pl.damiankaplon.beautyspace.treatment.domain.TreatmentService;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TreatmentServiceTests {

    @Mock
    DatabaseAdapter databaseAdapter;

    @InjectMocks
    TreatmentService service;

    TreatmentSupplier treatmentSupplier = new TreatmentSupplier();

    @Test
    public void returnsFirstPageWithSixTreatments() {
        List<Treatment> fromDb = new ArrayList<>();
        Interval.fromTo(1, 6).stream().forEach(i -> fromDb.add(treatmentSupplier.testTreatment()));
        when(databaseAdapter.findAll()).thenReturn(fromDb);

        Page<Treatment> treatmentPage = service.getTreatmentsPage(1);

        Assertions.assertEquals(1, treatmentPage.getTotalPages());
        Assertions.assertEquals(6, treatmentPage.getContent().size());
    }

    @Test
    public void returnsSecondPageWithOneTreatment() {
        List<Treatment> fromDb = new ArrayList<>();
        Interval.fromTo(1, 7).stream().forEach(i -> fromDb.add(treatmentSupplier.testTreatment()));
        when(databaseAdapter.findAll()).thenReturn(fromDb);

        Page<Treatment> treatmentPage = service.getTreatmentsPage(2);

        Assertions.assertEquals(2, treatmentPage.getTotalPages());
        Assertions.assertEquals(1, treatmentPage.getContent().size());
    }

    @Test
    public void editFullyTreatmentTest() {
        //GIVEN
        Treatment changes = Treatment.builder()
                .name("changed")
                .shortDescription("short desc changed")
                .fullDescription("full desc changed")
                .images(Set.of("changed.jpg"))
                .aproxTime(LocalTime.parse("03:59"))
                .types(Set.of("Full body"))
                .priceRange(999f, 9999f)
                .build();

        UUID toChange = UUID.fromString("123e4567-e89b-42d3-a456-556642440000");

        when(databaseAdapter.findByUuid(any(UUID.class)))
                .thenReturn(treatmentSupplier.testTreatmentWithUuid(toChange));

        when(databaseAdapter.update(any(Treatment.class)))
                .then(returnsFirstArg());

        //WHEN
        Treatment changedTreatment = service.editTreatment(changes, toChange);
        //THEN
        Assertions.assertEquals(changedTreatment.getUuid(), toChange);
        assertSimilarityWithoutUuid(changedTreatment, changes);

    }

    private static void assertSimilarityWithoutUuid(Treatment actual, Treatment expected) {
        Assertions.assertEquals(actual.getName(), expected.getName());
        Assertions.assertEquals(actual.getName(), expected.getName());
        Assertions.assertEquals(actual.getShortDescription(), expected.getShortDescription());
        Assertions.assertEquals(actual.getFullDescription(), expected.getFullDescription());
        Assertions.assertEquals(actual.getAproxTime().toString(), expected.getAproxTime().toString());
        Assertions.assertEquals(actual.getImagesSrcs(), expected.getImagesSrcs());
        Assertions.assertEquals(actual.getTypesNames(), expected.getTypesNames());
        Assertions.assertEquals(actual.getMinPrice(), expected.getMinPrice());
        Assertions.assertEquals(actual.getMaxPrice(), expected.getMaxPrice());
    }
}
