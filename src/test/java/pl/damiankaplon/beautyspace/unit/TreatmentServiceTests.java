package pl.damiankaplon.beautyspace.unit;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.AdditionalAnswers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import pl.damiankaplon.beautyspace.controller.form.TreatmentForm;
import pl.damiankaplon.beautyspace.picture.PictureDto;
import pl.damiankaplon.beautyspace.treatment.*;

import java.time.LocalTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TreatmentServiceTests {

    TreatmentService treatmentService;

    @Mock
    TreatmentRepository treatmentRepository;

    @BeforeEach
    void init() {
        this.treatmentService = new TreatmentService(treatmentRepository, new ModelMapper());
    }

    @Test
    public void shouldAddTreatment() {
        //GIVEN
        Treatment shouldBeReturned = Treatment
                .builder()
                .aproxTime(LocalTime.parse("01:00"))
                .fullDescription("test")
                .shortDescription("test")
                .name("test")
                .pictures(Lists.newArrayList(new Picture()))
                .priceRange(new PriceRange(100.0f, 500.0f))
                .id(1)
                .build();
        when(treatmentRepository.save(any(Treatment.class))).then(AdditionalAnswers.returnsFirstArg());

        TreatmentForm testForm = new TreatmentForm();
        testForm.setName("test");
        testForm.setAproxTime("01:00");
        testForm.setShortDescription("test");
        testForm.setFullDescription("test");
        testForm.setMinPrice("100.0");
        testForm.setMaxPrice("500.0");
        PictureDto pictureDto = new PictureDto("test");
        //WHEN
        Treatment underTesting = treatmentService.addNewTreatment(testForm, List.of(pictureDto));
        //THEN
        assertTreatmentsProperties(underTesting, shouldBeReturned);
    }

    private static void assertTreatmentsProperties(Treatment underTest, Treatment proper) {
        Assertions.assertAll(() -> {
            Assertions.assertEquals(underTest.getAproxTime().toString(), proper.getAproxTime().toString());
            Assertions.assertEquals(underTest.getFullDescription(), proper.getFullDescription());
            Assertions.assertEquals(underTest.getShortDescription(), proper.getShortDescription());
            Assertions.assertEquals(underTest.getName(), proper.getName());
            Assertions.assertEquals(underTest.getPriceRange().getMinPrice(), proper.getPriceRange().getMinPrice());
            Assertions.assertEquals(underTest.getPriceRange().getMaxPrice(), proper.getPriceRange().getMaxPrice());
        });
    }
}
