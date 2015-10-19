package by.auto.web.interceptors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UTF8EncodingHandlerInterceptor extends HandlerInterceptorAdapter {

    private static final String ENCODING = "UTF-8";

    @Override
    public void postHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler, final ModelAndView modelAndView) throws Exception {
        final String contentType = response.getContentType();
        if (StringUtils.isEmpty(contentType)) {
            response.setContentType("text/html;charset=UTF-8");
        } else {
            final String encoding = response.getCharacterEncoding();
            if (!ENCODING.equals(encoding)) {
                response.setCharacterEncoding(ENCODING);
            }
        }
    }
}
