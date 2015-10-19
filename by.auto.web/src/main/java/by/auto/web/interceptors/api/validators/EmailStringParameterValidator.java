package by.auto.web.interceptors.api.validators;

import by.auto.web.api.ApiConfig;
import by.auto.web.api.APIResponse;
import by.auto.web.interceptors.api.ApiParameterValidatorAdapter;
import org.hibernate.validator.internal.constraintvalidators.EmailValidator;
import org.springframework.web.servlet.support.RequestContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class EmailStringParameterValidator extends ApiParameterValidatorAdapter {

    @Override
    public boolean supportsParameter(final String parameterName) {
        return parameterName.equals(ApiConfig.EMAIL_PARAM_NAME);
    }

    @Override
    public APIResponse<?> validate(final HttpServletRequest request, final HttpServletResponse response, final String parameterValue) {
        final EmailValidator ev = new EmailValidator();
        if (!ev.isValid(parameterValue, null)) {
            final RequestContext requestContext = new RequestContext(request);
            final String error =
                    requestContext.getMessage("error.api.email.type.param.wrong.value", new Object[]{parameterValue});
            return APIResponse.error(error);
        }
        return null;
    }

}
