package filters;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;

import java.io.IOException;

public class LoggingFilter implements Filter {
    Logger logger;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.logger = (Logger) filterConfig.getServletContext().getAttribute("logger");
        this.logger.info("LoggingFilter: init");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        this.logger.info("LoggingFilter: doFilter");
        this.logger.info(((HttpServletRequest) servletRequest).getMethod());
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
