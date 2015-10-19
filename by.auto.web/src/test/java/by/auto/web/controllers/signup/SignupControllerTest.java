package by.auto.web.controllers.signup;

import by.auto.domain.common.Account;
import by.auto.domain.common.category.Category;
import by.auto.domain.testmothers.AccountMother;
import by.auto.domain.testmothers.CategoryMother;
import by.auto.service.AccountService;
import by.auto.service.exception.AccountAlreadyExistsException;
import by.auto.web.controllers.MvcControllerTest;
import by.auto.web.routes.Routes;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.util.ReflectionTestUtils;

import javax.inject.Inject;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@RunWith(SpringJUnit4ClassRunner.class)
public class SignupControllerTest extends MvcControllerTest {
    @Inject
    private AccountService accountService;

    private Account account;

    private Category category;

    @Before
    public void setUp() {
        account = AccountMother.createUserAccount();
        category = CategoryMother.createCategory();
        ReflectionTestUtils.setField(account, "id", "12345");
    }

    @Test
    public void shouldNavigateToSignUpView() throws Exception {
        mockMvc.perform(get(Routes.Signup.SIGNUP));
    }

    @Test
    public void shouldSuccessfullySignUpCompany() throws Exception {
        doNothing().when(accountService).create(any(Account.class));

        mockMvc.perform(post(Routes.Api.API_SIGNUP)
                .contentType(org.springframework.http.MediaType.APPLICATION_JSON)
                .content("{ \"login\":\"@login\",\n" +
                        " \"roleName\":\"Company\",\n" +
                        " \"emailPhone\":\"support@nabedai.by\",\n" +
                        " \"company\":\"Company name\",\n" +
                        " \"categoryId\":\"auto\",\n" +
                        " \"licenseAgreement\":true,\n" +
                        " \"password\":\"password\",\n" +
                        " \"confirmPassword\":\"password\"}"))
                .andExpect(jsonPath("$.type", is("SUCCESS")));
    }

    @Test
    public void shouldSuccessfullySignUpUser() throws Exception {
        doNothing().when(accountService).create(any(Account.class));
        mockMvc.perform(post(Routes.Api.API_SIGNUP)
                .contentType(org.springframework.http.MediaType.APPLICATION_JSON)
                .content("{ \"login\":\"логин@\",\n" +
                        " \"roleName\":\"User\",\n" +
                        " \"emailPhone\":\"291112233\",\n" +
                        " \"licenseAgreement\":true,\n" +
                        " \"password\":\"password\",\n" +
                        " \"confirmPassword\":\"password\"}"))
                .andExpect(jsonPath("$.type", is("SUCCESS")));
    }

    @Test
    public void shouldNotValidEmail() throws Exception {
        doNothing().when(accountService).create(any(Account.class));
        mockMvc.perform(post(Routes.Api.API_SIGNUP)
                .contentType(org.springframework.http.MediaType.APPLICATION_JSON)
                .content("{ \"login\":\"test@email.ru\",\n" +
                        " \"roleName\":\"User\",\n" +
                        " \"emailPhone\":\"testmail.ru\",\n" +
                        " \"licenseAgreement\":true,\n" +
                        " \"password\":\"password\",\n" +
                        " \"confirmPassword\":\"password\"}"))
                .andExpect(jsonPath("$.type", is("ERROR")))
                .andExpect(jsonPath("$.data.emailPhone", notNullValue()));
    }

    @Test
    public void shouldNotValidPhone() throws Exception {
        doNothing().when(accountService).create(any(Account.class));
        mockMvc.perform(post(Routes.Api.API_SIGNUP)
                .contentType(org.springframework.http.MediaType.APPLICATION_JSON)
                .content("{ \"login\":\"test@email.ru\",\n" +
                        " \"roleName\":\"User\",\n" +
                        " \"emailPhone\":\"29111\",\n" +
                        " \"licenseAgreement\":true,\n" +
                        " \"password\":\"password\",\n" +
                        " \"confirmPassword\":\"password\"}"))
                .andExpect(jsonPath("$.type", is("ERROR")))
                .andExpect(jsonPath("$.data.emailPhone", notNullValue()));
    }

    @Test
    public void shouldErrorWithAdminRoleName() throws Exception {
        doNothing().when(accountService).create(any(Account.class));
        mockMvc.perform(post(Routes.Api.API_SIGNUP)
                .contentType(org.springframework.http.MediaType.APPLICATION_JSON)
                .content("{ \"login\":\"test@email.ru\",\n" +
                        " \"roleName\":\"Admin\",\n" +
                        " \"emailPhone\":\"support@nabedai.by\",\n" +
                        " \"licenseAgreement\":true,\n" +
                        " \"password\":\"password\",\n" +
                        " \"confirmPassword\":\"password\"}"))
                .andExpect(jsonPath("$.type", is("ERROR")));
    }

    @Test
    public void shouldPostWithInvalidRoleName() throws Exception {
        doNothing().when(accountService).create(any(Account.class));
        mockMvc.perform(post(Routes.Api.API_SIGNUP)
                .contentType(org.springframework.http.MediaType.APPLICATION_JSON)
                .content("{ \"login\":\"test@email.ru\",\n" +
                        " \"roleName\":\"Role\",\n" +
                        " \"emailPhone\":\"support@nabedai.by\",\n" +
                        " \"licenseAgreement\":true,\n" +
                        " \"password\":\"password\",\n" +
                        " \"confirmPassword\":\"password\"}"))
                .andExpect(jsonPath("$.type", is("ERROR")))
                .andExpect(jsonPath("$.data.roleName", notNullValue()));
    }

    @Test
    public void shouldReturnAccountExistsException() throws Exception {
        willThrow(AccountAlreadyExistsException.class).given(accountService).create(any(Account.class));
        mockMvc.perform(post(Routes.Api.API_SIGNUP)
                .contentType(org.springframework.http.MediaType.APPLICATION_JSON)
                .content("{ \"login\":\"test@email.ru\",\n" +
                        " \"roleName\":\"Company\",\n" +
                        " \"emailPhone\":\"support@nabedai.by\",\n" +
                        " \"licenseAgreement\":true,\n" +
                        " \"password\":\"password\",\n" +
                        " \"confirmPassword\":\"password\"}"))
                .andExpect(jsonPath("$.type", is("ERROR")));
    }

    @Test
    public void shouldNotConfirmPassword() throws Exception {
        mockMvc.perform(post(Routes.Api.API_SIGNUP)
                .contentType(org.springframework.http.MediaType.APPLICATION_JSON)
                .content("{ \"login\":\"test@email.ru\",\n" +
                        " \"roleName\":\"Company\",\n" +
                        " \"emailPhone\":\"support@nabedai.by\",\n" +
                        " \"licenseAgreement\":true,\n" +
                        " \"password\":\"password\",\n" +
                        " \"confirmPassword\":\"passwor1\"}"))
                .andExpect(jsonPath("$.type", is("ERROR")))
                .andExpect(jsonPath("$.data.confirmPassword", notNullValue()));
    }

    @Test
    public void shouldShortPassword() throws Exception {
        mockMvc.perform(post(Routes.Api.API_SIGNUP)
                .contentType(org.springframework.http.MediaType.APPLICATION_JSON)
                .content("{ \"login\":\"test@email.ru\",\n" +
                        " \"roleName\":\"Company\",\n" +
                        " \"emailPhone\":\"support@nabedai.by\",\n" +
                        " \"licenseAgreement\":true,\n" +
                        " \"password\":\"pas\",\n" +
                        " \"confirmPassword\":\"pas\"}"))
                .andExpect(jsonPath("$.type", is("ERROR")))
                .andExpect(jsonPath("$.data.password", notNullValue()));
    }

    @Test
    public void shouldNotLicenceAgreement() throws Exception {
        mockMvc.perform(post(Routes.Api.API_SIGNUP)
                .contentType(org.springframework.http.MediaType.APPLICATION_JSON)
                .content("{ \"login\":\"test@email.ru\",\n" +
                        " \"roleName\":\"Company\",\n" +
                        " \"emailPhone\":\"support@nabedai.by\",\n" +
                        " \"licenseAgreement\":false,\n" +
                        " \"password\":\"password\",\n" +
                        " \"confirmPassword\":\"password\"}"))
                .andExpect(jsonPath("$.type", is("ERROR")))
                .andExpect(jsonPath("$.data.licenseAgreement", notNullValue()));
    }
}