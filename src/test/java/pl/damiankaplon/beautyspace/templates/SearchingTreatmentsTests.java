package pl.damiankaplon.beautyspace.templates;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import pl.damiankaplon.beautyspace.controller.AccountController;
import pl.damiankaplon.beautyspace.controller.TreatmentController;
import pl.damiankaplon.beautyspace.picture.PictureService;
import pl.damiankaplon.beautyspace.treatment.PriceRange;
import pl.damiankaplon.beautyspace.treatment.Treatment;
import pl.damiankaplon.beautyspace.treatment.TreatmentBodyPart;
import pl.damiankaplon.beautyspace.treatment.TreatmentService;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.when;

@WebMvcTest(value = {TreatmentController.class, AccountController.class})
public class SearchingTreatmentsTests {

    @MockBean
    TreatmentService treatmentService;
    @MockBean
    PictureService pictureService;
    @MockBean
    AccountController accountController;

    @Autowired
    MockMvc mockMvc;

    @Test
    public void shouldSuccessfullyReturnDetailedViewTreatmentPageByUuid() throws Exception {
        //GIVEN
        UUID testUuid = UUID.randomUUID();
        Treatment stubTreatment = Treatment.builder()
                        .uuid(testUuid)
                        .name("test")
                        .priceRange(new PriceRange(100f, 1000f))
                        .build();
        when(treatmentService.getTreatment(testUuid)).thenReturn(stubTreatment);
        //WHEN & THEN
        mockMvc.perform(MockMvcRequestBuilders.get("/treatment/uuid/" + testUuid))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void shouldSuccessfullyReturnViewWithTreatmentsMatchingName() throws Exception {
        //GIVEN
        List<Treatment> stubTreatments = List.of(
                Treatment.builder()
                        .uuid(UUID.randomUUID())
                        .name("test")
                        .pictures(Collections.emptyList())
                        .priceRange(new PriceRange(100f, 1000f))
                        .build()
        );
        when(treatmentService.getAllByName("test")).thenReturn(stubTreatments);
        //WHEN & THEN
        mockMvc.perform(MockMvcRequestBuilders.get("/treatment/name/test"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void shouldSuccessfullyReturnViewWithTreatmentsMatchingType() throws Exception {
        //GIVEN
        List<Treatment> stubTreatments = List.of(
                Treatment.builder()
                        .uuid(UUID.randomUUID())
                        .name("test")
                        .bodyParts(List.of(TreatmentBodyPart.FACE))
                        .pictures(Collections.emptyList())
                        .priceRange(new PriceRange(100f, 1000f))
                        .build()
        );
        when(treatmentService.getAllByType(TreatmentBodyPart.FACE)).thenReturn(stubTreatments);
        //WHEN & THEN
        mockMvc.perform(MockMvcRequestBuilders.get("/treatment/type/FACE"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

}
