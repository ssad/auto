package by.auto.web.controllers.signin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.MailSendException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.RequestContext;
import by.auto.domain.common.enums.TokenType;
import by.auto.service.AccountService;
import by.auto.service.exception.ForgotAccountException;
import by.auto.service.security.AuthenticationService;
import by.auto.web.api.APIResponse;
import by.auto.web.exception.HandledWebLayerException;
import by.auto.web.interceptors.api.Api;
import by.auto.web.json.MediaType;
import by.auto.web.routes.Routes;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;

@Controller
public class ForgotPasswordController {
    @Inject
    private AccountService accountService;

    @Inject
    private AuthenticationService authenticationService;

    public static final Logger LOGGER = LoggerFactory.getLogger(ForgotPasswordController.class);

//    @RequestMapping(value = Routes.Profile.FORGOT_PASSWORD_UPDATE, method = RequestMethod.GET)
//    public Map<String, String> getUpdatePasswordPage(
//            @RequestParam(value = "token", required = true) final String token) {
//        final Map<String, String> result = new HashMap<String, String>();
//        result.put("token", token);
//        final Account account = accountService.findByGeneratedToken(token);
//        if (account != null) {
//            result.put("email", account.getEmail());
//        }
//        return result;
//    }

    @Api
    @RequestMapping(value = Routes.Api.API_FORGOT_PASSWORD, produces = MediaType.APPLICATION_JSON_CHARSET_UTF8, method = RequestMethod.POST)
    @ResponseBody
    public APIResponse<?> generateToken(@RequestBody final String email, final RequestContext context) {
        try {
            accountService.generatePasswordToken(email, TokenType.ForgotPassword);
        } catch (final ForgotAccountException e) {
            throw new HandledWebLayerException(context.getMessage("forgot.password.error.not.found.email", new Object[]{email}));
        } catch (final MailSendException e) {
            throw new HandledWebLayerException(context.getMessage("forgot.password.error.email.sending", new Object[]{email}));
        }
        Map<String, Object> data = new HashMap<>();
        return APIResponse.success(data, context.getMessage("forgot.password.success.token.generate", new Object[]{email}));
    }

//    @RequestMapping(value = Routes.Api.API_FORGOT_PASSWORD_UPDATE, produces = MediaType.APPLICATION_JSON_CHARSET_UTF8, method = RequestMethod.POST)
//    @ResponseBody
//    public APIResponse<?> updatePasswordUsingToken(
//            @RequestBody @Valid final UpdateTokenPasswordJSONRequest updateTokenPassword,
//            final RequestContext context, final HttpServletRequest servletRequest) {
//        try {
//            final Account account = accountService.updatePasswordUsingToken(updateTokenPassword.getToken(), PasswordEncoder.encode(updateTokenPassword.getPassword()), TokenType.ForgotPassword);
//            AccountUtils.signin(account);
//            return APIResponse.success(context.getMessage("forgot.password.success.password.changed"));
//        } catch (final ForgotAccountException e) {
//            throw new HandledWebLayerException(context.getMessage("forgot.password.error.token.wrong.or.expired"));
//        }
//    }
}