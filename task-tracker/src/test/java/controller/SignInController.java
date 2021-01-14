package controller;

import org.junit.Before;
import org.junit.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

public class SignInController {

    private MockMvc mockMvc;

    @Before
    public void setup(){
        SignInController signInController = new SignInController();
        mockMvc= MockMvcBuilders.standaloneSetup(signInController).build();
    }

    @Test
    public void getSignInFormShouldReturnStatusOkAndLoginFormAsViewName() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("forms/login"));
    }
}