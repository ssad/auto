package by.auto.web.interceptors.api;

import by.auto.web.api.APIResponse;
import org.springframework.core.MethodParameter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ApiParameterValidatorAdapter implements ApiParameterValidator {

    /**
     * This implementation always returns <code>false</code>.
     */
    @Override
    public boolean supportsParameter(final Class<?> parameterType) {
        return false;
    }

    /**
     * This implementation always returns <code>false</code>.
     */
    @Override
    public boolean supportsParameter(final String parameterName) {
        return false;
    }

    /**
     * This implementation always returns <code>null</code>.
     */
    @Override
    public APIResponse<?> validate(final HttpServletRequest request, final HttpServletResponse response, final MethodParameter parameter) {
        return null;
    }

    /**
     * This implementation always returns <code>null</code>.
     */
    @Override
    public APIResponse<?> validate(final HttpServletRequest request, final HttpServletResponse response, final String parameterValue) {
        return null;
    }
}
