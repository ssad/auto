package by.auto.web.controllers.signup;

import by.auto.domain.common.category.Category;
import by.auto.web.controllers.utils.ControllerUtils;
import by.auto.web.routes.Routes;
import by.auto.web.utils.AccountUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.ConversionService;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.support.RequestContext;
import by.auto.domain.common.Account;
import by.auto.service.AccountService;
import by.auto.service.exception.AccountAlreadyExistsException;
import by.auto.web.api.APIResponse;
import by.auto.web.api.json.JSONRequests.SignUpJSONRequest;
import by.auto.web.exception.HandledWebLayerException;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.*;

@Controller
public class SignupController {
    @Inject
    private AccountService accountService;
    @Inject
    private ConversionService conversionService;

    public static final Logger LOGGER = LoggerFactory.getLogger(SignupController.class);

    @RequestMapping(value = Routes.Signup.SIGNUP, method = RequestMethod.GET)
    public String signup(final Model model) {
        List<Category> categories = new ArrayList<>();
        //todo temporary add
        categories.add(new Category("cat1", null, null));
        categories.add(new Category("cat2", null, null));
        model.addAttribute("categories", categories);
        return "signup";
    }

    @RequestMapping(value = Routes.Api.API_SIGNUP, method = RequestMethod.POST)
    @ResponseBody
    public APIResponse<?> signUp(@Valid @RequestBody final SignUpJSONRequest signUpJSONRequest,
                                 final RequestContext context) {
        try {
            Account account = conversionService.convert(signUpJSONRequest, Account.class);
            accountService.create(account);

            Map<String, Object> data = new HashMap<>();
            return APIResponse.success(data);
        } catch (AccountAlreadyExistsException ex) {
            throw new HandledWebLayerException(context.getMessage("account.duplicateEmail"));
        } catch (MailException me) {
            LOGGER.error("Error during registration " + signUpJSONRequest.getEmailPhone(), me);
            throw new HandledWebLayerException(context.getMessage("account.email.failed", Arrays.asList(signUpJSONRequest.getEmailPhone())));
        } catch (Exception e) {
            LOGGER.error("Error during registration " + signUpJSONRequest.getEmailPhone(), e);
            throw new HandledWebLayerException(e.getMessage());
        }
    }

    @RequestMapping(value = Routes.Signup.SIGNUP + "/confirm", method = RequestMethod.GET)
    public String confirm(@RequestParam(value = "token", required = true) final String token, final WebRequest request, final HttpServletRequest servletRequest) {
        final Account account = accountService.activate(token);
        AccountUtils.signin(account);
        ControllerUtils.addSuccessMessage(request, "Success");
        return ControllerUtils.redirectTo(Routes.Profile.PROFILE);
    }
}
