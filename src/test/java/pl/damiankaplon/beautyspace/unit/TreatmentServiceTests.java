package pl.damiankaplon.beautyspace.unit;

import org.eclipse.collections.api.block.procedure.primitive.IntProcedure;
import org.eclipse.collections.impl.list.Interval;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.mock.web.MockMultipartFile;
import pl.damiankaplon.beautyspace.TreatmentSupplier;
import pl.damiankaplon.beautyspace.treatment.adapters.db.SqlAdapter;
import pl.damiankaplon.beautyspace.treatment.domain.Treatment;
import pl.damiankaplon.beautyspace.treatment.domain.TreatmentService;
import pl.damiankaplon.beautyspace.treatment.domain.dtos.Form;
import pl.damiankaplon.beautyspace.treatment.domain.dtos.ImageDto;
import pl.damiankaplon.beautyspace.treatment.domain.ports.outgoing.ImageService;
import pl.damiankaplon.beautyspace.treatment.domain.ports.outgoing.ImageServiceException;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TreatmentServiceTests {

    @Mock
    SqlAdapter databaseAdapter;

    @Mock
    ImageService imageService;

    @InjectMocks
    TreatmentService service;

    TreatmentSupplier treatmentSupplier = new TreatmentSupplier();

    @Test
    public void returnsFirstPageWithSixTreatments() {
        List<Treatment> fromDb = new ArrayList<>();
        Interval.fromTo(1, 6).forEach((IntProcedure) i -> fromDb.add(treatmentSupplier.random()));
        when(databaseAdapter.findAll()).thenReturn(fromDb);

        Page<Treatment> treatmentPage = service.getTreatmentsPage(1);

        Assertions.assertEquals(1, treatmentPage.getTotalPages());
        Assertions.assertEquals(6, treatmentPage.getContent().size());
    }

    @Test
    public void returnsSecondPageWithOneTreatment() {
        List<Treatment> fromDb = new ArrayList<>();
        Interval.fromTo(1, 7).forEach((IntProcedure) i -> fromDb.add(treatmentSupplier.random()));
        when(databaseAdapter.findAll()).thenReturn(fromDb);

        Page<Treatment> treatmentPage = service.getTreatmentsPage(2);

        Assertions.assertEquals(2, treatmentPage.getTotalPages());
        Assertions.assertEquals(1, treatmentPage.getContent().size());
    }

    @Test
    public void returnsAllClientsRequiredTreatmentTypes() {
        Set<String> clientRequires = Set.of("Face", "Body", "Cosmetic");
        Set<String> types = new HashSet<>(service.getAllTypes());
        Assertions.assertEquals(clientRequires, types);
    }

    @Test
    public void editFullyTreatmentTest() throws ImageServiceException {
        //GIVEN
        Form changes = new Form();
        changes.setChosenTypes(Set.of("Full body"));
        changes.setName("changed");
        changes.setShortDescription("short desc changed");
        changes.setFullDescription("full desc changed");
        changes.setAproxTime("4567");
        changes.setChosenTypes(Set.of("Body"));
        changes.setMinPrice("100");
        changes.setMaxPrice("1000");
        MockMultipartFile[] images = new MockMultipartFile[] {
                        new MockMultipartFile("image1.jpg", new byte[]{12, 12, 12}),
                        new MockMultipartFile("image2.jpg", new byte[]{12, 12, 12})
        };

        UUID toChange = UUID.fromString("123e4567-e89b-42d3-a456-556642440000");

        when(databaseAdapter.findByUuid(any(UUID.class)))
                .thenReturn(treatmentSupplier.randomWithUuid(toChange));
        when(imageService.upload(any()))
                .thenReturn(List.of(new ImageDto("image1.jpg"), new ImageDto("image2.jpg")));
        when(databaseAdapter.update(any(Treatment.class)))
                .then(returnsFirstArg());

        //WHEN
        Treatment changedTreatment = service.editTreatment(toChange, changes, images);
        //THEN
        Assertions.assertEquals(changedTreatment.getUuid(), toChange);
        Assertions.assertEquals(changes.getName(), changedTreatment.getName());
        Assertions.assertEquals(changes.getName(), changedTreatment.getName());
        Assertions.assertEquals(changes.getShortDescription(), changedTreatment.getShortDescription());
        Assertions.assertEquals(changes.getFullDescription(), changedTreatment.getFullDescription());
        Assertions.assertEquals(Long.valueOf(changes.getAproxTime()), changedTreatment.getAproxTime().getSeconds());
        Assertions.assertEquals(changes.getChosenTypes(), changedTreatment.getTypesNames());
        Assertions.assertEquals(changes.getMinPriceValue(), changedTreatment.getMinPrice());
        Assertions.assertEquals(changes.getMaxPriceValue(), changedTreatment.getMaxPrice());

        Set<String> srcs = Stream.of(images)
                .map(MockMultipartFile::getName)
                .collect(Collectors.toSet());
        Assertions.assertEquals(srcs, new HashSet<>(changedTreatment.getImagesSrcs()));
    }

}
