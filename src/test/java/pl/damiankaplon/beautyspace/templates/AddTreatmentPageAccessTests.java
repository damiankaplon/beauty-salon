package pl.damiankaplon.beautyspace.templates;

import com.google.common.collect.Lists;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;
import pl.damiankaplon.beautyspace.controller.AccountController;
import pl.damiankaplon.beautyspace.controller.TreatmentController;
import pl.damiankaplon.beautyspace.picture.PictureDto;
import pl.damiankaplon.beautyspace.picture.PictureService;
import pl.damiankaplon.beautyspace.treatment.Treatment;
import pl.damiankaplon.beautyspace.treatment.TreatmentRepository;
import pl.damiankaplon.beautyspace.treatment.TreatmentService;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.testSecurityContext;

@SpringBootTest
@AutoConfigureMockMvc
public class AddTreatmentPageAccessTests {

    @MockBean
    TreatmentRepository treatmentRepository;
    @MockBean
    PictureService pictureService;
    @MockBean
    AccountController accountController;

    @Autowired
    MockMvc mockMvc;

    @Test
    @WithMockUser(authorities = {"WRITE_PRIVILEGE"})
    public void shouldAllowToAccessPage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/treatment/add"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockUser(authorities = {"READ_AUTHORITY"})
    public void shouldNotAllowToAccessPage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/treatment/add"))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    @WithMockUser(authorities = {"WRITE_PRIVILEGE"})
    public void shouldSuccessfullyAddPicture() throws Exception {
        MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
        form.add("name", "test");
        form.add("shortDescription", "test");
        form.add("fullDescription", "test");
        form.add("aproxTime", "00:10:00");
        form.add("minPrice", "100.0");
        form.add("maxPrice", "1000.0");

        MockMultipartFile pic1 = new MockMultipartFile("pic", "test1.jpg", ".jpg", "pic1".getBytes());
        MockMultipartFile pic2 = new MockMultipartFile("pic", "test2.jpg", ".jpg", "pic2".getBytes());
        MultipartFile[] pics = new MultipartFile[]{pic1, pic2};

        when(pictureService.upload(ArgumentMatchers.anyList()))
                .thenReturn(stubPictureServiceUploadMethod(Lists.newArrayList(pics)));
        when(treatmentRepository.save(any(Treatment.class)))
                .then(AdditionalAnswers.returnsFirstArg());

        mockMvc.perform(MockMvcRequestBuilders.multipart("/treatment/add")
                        .file(pic1)
                        .file(pic2)
                        .params(form)
                        .with(csrf()))
                .andExpect(MockMvcResultMatchers.model().attribute("treatment", new BaseMatcher<Treatment>() {
                    @Override
                    public boolean matches(Object actual) {
                        Treatment treatment = (Treatment) actual;
                        return  treatment.getAproxTime().getMinute() == 10 &&
                                treatment.getPriceRange().getMinPrice().equals(Float.valueOf(form.get("minPrice").get(0))) &&
                                treatment.getPriceRange().getMaxPrice().equals(Float.valueOf(form.get("maxPrice").get(0))) &&
                                treatment.getName().equals(form.get("name").get(0)) &&
                                treatment.getShortDescription().equals(form.get("shortDescription").get(0)) &&
                                treatment.getFullDescription().equals(form.get("fullDescription").get(0)) &&
                                treatment.getPictures().size() == pics.length;
                    }

                    @Override
                    public void describeTo(Description description) {
                        description.appendText("While asserting response page model which contains added treatment" +
                                "assertion differences occurred");
                    }
                }))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
    private List<PictureDto> stubPictureServiceUploadMethod(List<MultipartFile> pictures) throws IOException {
        List<PictureDto> dtos = new ArrayList<>();
        for (MultipartFile picture : pictures) {
            LocalDateTime timestamp = LocalDateTime.now();
            String pathForObjects = "/ServicesPictures/"
                    + timestamp
                    + picture.getOriginalFilename();
            dtos.add(new PictureDto(pathForObjects));
        }
        return dtos;
    }
}

