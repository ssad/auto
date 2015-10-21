package by.auto.web.resolvers;

import org.springframework.core.Ordered;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <a href="http://steveliles.github.com/configuring_global_exception_handling_in_spring_mvc.html">
 * http://steveliles.github.com/configuring_global_exception_handling_in_spring_mvc.html
 * </a>
 */
public class UTF8HandlerExceptionResolver implements HandlerExceptionResolver, Ordered {
    @Override
    public ModelAndView resolveException(final HttpServletRequest request, final HttpServletResponse response, final Object handler, final Exception ex) {
        response.setCharacterEncoding("UTF-8");

        return null; // default processing, delegate to others
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE; // we are the first
    }
}
