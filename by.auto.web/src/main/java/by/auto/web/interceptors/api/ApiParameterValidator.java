package by.auto.web.interceptors.api;

import by.auto.web.api.APIResponse;
import org.springframework.core.MethodParameter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface ApiParameterValidator {
    /**
     * Whether the given parameter type is supported by this validator.
     *
     * @param parameterType parameter type to support
     * @return {@code true} if this validator supports the supplied parameter type;
     *         {@code false} otherwise
     */
    public boolean supportsParameter(Class<?> parameterType);

    /**
     * Whether the given parameter name is supported by this validator.
     *
     * @param parameterName parameter name to support
     * @return {@code true} if this validator supports the supplied parameter name;
     *         {@code false} otherwise
     */
    public boolean supportsParameter(String parameterName);

    /**
     * Validate the specified parameter.
     * There is a chain of validators, so each validator can decide to abort the execution chain,
     * typically sending a HTTP error or writing a custom response.
     *
     * @param request   current HTTP request
     * @param response  current HTTP response
     * @param parameter parameter for evaluation
     * @return <code>null</code> if the execution chain should proceed with the
     *         next validator in the chain. Else, consume the returned response and return it to the client.
     */
    public APIResponse<?> validate(HttpServletRequest request, HttpServletResponse response, MethodParameter parameter);

    /**
     * Validate the specified parameter value.
     * There is a chain of validators, so each validator can decide to abort the execution chain,
     * typically sending a HTTP error or writing a custom response.
     *
     * @param request        current HTTP request
     * @param response       current HTTP response
     * @param parameterValue parameter value for evaluation for supported parameter name ({@link #supportsParameter(String)})
     * @return <code>null</code> if the execution chain should proceed with the
     *         next validator in the chain. Else, consume the returned response and return it to the client.
     */
    public APIResponse<?> validate(HttpServletRequest request, HttpServletResponse response, String parameterValue);
}
