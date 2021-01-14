package controller;

import org.junit.Before;
import org.junit.Test;
import org.mockito.*;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.otus.taskmanager.controller.SignUpController;
import ru.otus.taskmanager.model.user.User;
import ru.otus.taskmanager.service.UserService;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class RegisterControllerTest {

    @Mock
    UserService userServiceMock;

    @InjectMocks
    SignUpController signUpController;

    private MockMvc mockMvc;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(signUpController).build();
    }

    @Test
    public void getRegisterFormShouldReturnStatusOkAndRegisterFormAsViewNameAndUserAsAttribute() throws Exception {

        verifyZeroInteractions(userServiceMock);

        mockMvc.perform(get("/register"))
                .andExpect(status().isOk())
                .andExpect(view().name("forms/register"))
                .andExpect(model().attribute("user", instanceOf(User.class)));
    }
}