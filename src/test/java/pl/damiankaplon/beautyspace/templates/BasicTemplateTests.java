package pl.damiankaplon.beautyspace.templates;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import pl.damiankaplon.beautyspace.controller.AccountController;
import pl.damiankaplon.beautyspace.controller.BasicController;

@WebMvcTest(value = {BasicController.class, AccountController.class})
public class BasicTemplateTests {

    @MockBean
    AccountController accountController;

    @Autowired
    MockMvc mockMvc;

    @Test
    public void shouldSuccessfullyReturnContactPage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/contact"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("contact"));
    }

    @Test
    public void shouldSuccessfullyReturnHomePage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("index"));

        mockMvc.perform(MockMvcRequestBuilders.get("/home"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("index"));

        mockMvc.perform(MockMvcRequestBuilders.get("/index.html"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("index"));
    }
}
