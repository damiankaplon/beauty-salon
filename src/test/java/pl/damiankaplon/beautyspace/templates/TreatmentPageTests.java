package pl.damiankaplon.beautyspace.templates;

import org.assertj.core.util.Lists;
import org.eclipse.collections.impl.list.Interval;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import pl.damiankaplon.beautyspace.controller.AccountController;
import pl.damiankaplon.beautyspace.controller.TreatmentController;
import pl.damiankaplon.beautyspace.picture.PictureService;
import pl.damiankaplon.beautyspace.treatment.Treatment;
import pl.damiankaplon.beautyspace.treatment.TreatmentType;
import pl.damiankaplon.beautyspace.treatment.TreatmentService;

import java.util.List;

import static org.mockito.Mockito.when;


@WebMvcTest(value = {TreatmentController.class, AccountController.class})
public class TreatmentPageTests {

    @MockBean
    TreatmentService treatmentService;
    @MockBean
    PictureService pictureService;
    @MockBean
    AccountController accountController;

    @Autowired
    MockMvc mockMvc;

    @Test
    public void shouldReturnEmptyTemplateWithNoTreatments() throws Exception {
       //GIVEN
        Page<Treatment> treatmentPage = new CustomStubs.StubTreatmentsPage();
        when(treatmentService.geTreatmentsPage(PageRequest.of(0, 6)))
                .thenReturn(treatmentPage);
        //WHEN & THEN
        mockMvc.perform(MockMvcRequestBuilders.get("/treatment"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("treatment"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("dtoPage", "types", "pageNumbers"))
                .andExpect(MockMvcResultMatchers.model().attribute("dtoPage", new BaseMatcher<Page<Treatment>>() {
                    @Override
                    public boolean matches(Object actual) {
                        Page<Treatment> page = (Page<Treatment>) actual;
                        return page.getTotalPages() == 0;
                    }

                    @Override
                    public void describeTo(Description description) {
                        description.appendText("Page count should be 0");
                    }
                }))
                .andExpect(MockMvcResultMatchers.model().attributeHasNoErrors("dtoPage"));
    }

    @Test
    public void shouldContain5Pages() throws Exception {
        class SixPagesStub extends CustomStubs.StubTreatmentsPage {
            @Override
            public int getTotalPages() {
                return 5;
            }
        }
        when(treatmentService.geTreatmentsPage(PageRequest.of(0, 6)))
                .thenReturn(new SixPagesStub());

        mockMvc.perform(MockMvcRequestBuilders.get("/treatment"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("treatment"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("dtoPage", "types", "pageNumbers"))
                .andExpect(MockMvcResultMatchers.model().attributeHasNoErrors("dtoPage"))
                .andExpect(MockMvcResultMatchers.model().attribute("pageNumbers", new BaseMatcher<Page<Treatment>>() {
                    @Override
                    public boolean matches(Object actual) {
                        Interval pageNumber = (Interval) actual;
                        return pageNumber.size() == 5;
                    }

                    @Override
                    public void describeTo(Description description) {
                        description.appendText("Pages count should be 5");
                    }
                }));
    }

    @Test
    public void shouldContain2Pages() throws Exception {
        class TwoPagesStub extends CustomStubs.StubTreatmentsPage {
            @Override
            public int getTotalPages() {
                return 2;
            }
        }
        when(treatmentService.geTreatmentsPage(PageRequest.of(0, 6)))
                .thenReturn(new TwoPagesStub());

        mockMvc.perform(MockMvcRequestBuilders.get("/treatment"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("treatment"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("dtoPage", "types", "pageNumbers"))
                .andExpect(MockMvcResultMatchers.model().attributeHasNoErrors("dtoPage"))
                .andExpect(MockMvcResultMatchers.model().attribute("pageNumbers", new BaseMatcher<Page<Treatment>>() {
                    @Override
                    public boolean matches(Object actual) {
                        Interval pageNumber = (Interval) actual;
                        return pageNumber.size() == 2;
                    }

                    @Override
                    public void describeTo(Description description) {
                        description.appendText("Pages count should be 2");
                    }
                }));
    }

    @Test
    public void shouldContainAllBodyTypesInModel() throws Exception {
        class TwoPagesStub extends CustomStubs.StubTreatmentsPage {
            @Override
            public int getTotalPages() {
                return 2;
            }
        }
        when(treatmentService.geTreatmentsPage(PageRequest.of(0, 6)))
                .thenReturn(new TwoPagesStub());

        mockMvc.perform(MockMvcRequestBuilders.get("/treatment"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("treatment"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("dtoPage", "types", "pageNumbers"))
                .andExpect(MockMvcResultMatchers.model().attributeHasNoErrors("dtoPage"))
                .andExpect(MockMvcResultMatchers.model().attribute("types", new BaseMatcher<>() {
                    @Override
                    public boolean matches(Object actual) {
                        TreatmentType[] types = (TreatmentType[]) actual;
                        List<TreatmentType> treatmentBodyPartList = Lists.newArrayList(TreatmentType.values());
                        return List.of(types).containsAll(treatmentBodyPartList);
                    }

                    @Override
                    public void describeTo(Description description) {
                        description.appendText("Model attribute doesn't contain all body types available");
                    }
                }));
    }
}
