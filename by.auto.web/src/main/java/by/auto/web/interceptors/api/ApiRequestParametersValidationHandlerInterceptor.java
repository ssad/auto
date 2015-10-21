package by.auto.web.interceptors.api;

import by.auto.web.api.APIResponse;
import by.auto.web.json.MediaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.MethodParameter;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class ApiRequestParametersValidationHandlerInterceptor extends HandlerInterceptorAdapter {
    @Inject
    private ObjectMapper objectMapper;

    private List<ApiParameterValidator> validators = new ArrayList<>();

    public void setValidators(final List<ApiParameterValidator> validators) {
        this.validators = validators;
    }

    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler) throws Exception {
        if (isApiMethod(handler)) {
            if (validateMethodParameters(request, response, (HandlerMethod) handler)) return false;

            if (validateRequestParameters(request, response)) return false;
        }

        return true;
    }

    private boolean isApiMethod(final Object handler) {
        return handler instanceof HandlerMethod && ((HandlerMethod) handler).getMethod().isAnnotationPresent(Api.class);
    }

    private boolean validateMethodParameters(final HttpServletRequest request, final HttpServletResponse response, final HandlerMethod handler) throws IOException {
        final MethodParameter[] methodParameters = handler.getMethodParameters();
        for (final MethodParameter parameter : methodParameters) {
            for (final ApiParameterValidator validator : validators) {
                if (validator.supportsParameter(parameter.getParameterType())) {
                    final APIResponse<?> result = validator.validate(request, response, parameter);
                    if (result != null) {
                        sendError(response, result);

                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean validateRequestParameters(final HttpServletRequest request, final HttpServletResponse response) throws IOException {
        final Enumeration<String> requestParameters = request.getParameterNames();
        while (requestParameters.hasMoreElements()) {
            final String parameterName = requestParameters.nextElement();
            for (final ApiParameterValidator validator : validators) {
                if (validator.supportsParameter(parameterName)) {
                    final APIResponse<?> result = validator.validate(request, response, request.getParameter(parameterName));
                    if (result != null) {
                        sendError(response, result);

                        return true;
                    }
                }
            }
        }
        return false;
    }

    private void sendError(final HttpServletResponse response, final APIResponse<?> result) throws IOException {
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        response.setContentType(MediaType.APPLICATION_JSON_CHARSET_UTF8);
        response.getWriter().write(objectMapper.writeValueAsString(result));
    }
}
