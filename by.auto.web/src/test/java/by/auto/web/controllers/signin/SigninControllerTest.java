package by.auto.web.controllers.signin;


import by.auto.web.controllers.MvcControllerStatusAlwaysOkTest;
import by.auto.web.routes.Routes;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringJUnit4ClassRunner.class)
public class SigninControllerTest extends MvcControllerStatusAlwaysOkTest {
    @Test
    public void shouldNavigateToSignInView() throws Exception {
        mockMvc.perform(get(Routes.SIGNIN))
                .andExpect(view().name("signin"));
    }
}
