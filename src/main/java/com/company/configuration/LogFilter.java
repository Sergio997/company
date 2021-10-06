package com.company.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class LogFilter implements Filter {

    @Override
    public void init(final FilterConfig filterConfig) {
        log.info("Initializing filter :{}", this);
    }

    @Override
    public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain)
            throws IOException, ServletException {
        requestLog((HttpServletRequest) request);
        chain.doFilter(request, response);
        responseLog((HttpServletResponse) response);
    }

    @Override
    public void destroy() {
        log.warn("Destructing filter :{}", this);
    }

    private void requestLog(HttpServletRequest request) {
        request.getRemoteAddr();
        String line = String.format("Request, method: %s, url:  %s, remote address:  %s, headers: %s",
                request.getMethod(), request.getRequestURL(), request.getRemoteAddr(), request.getHeaderNames());
        log.debug(line);
    }

    private void responseLog(HttpServletResponse response) {
        String line = String.format("Response, content type: %s, status:  %s", response.getContentType(), response.getStatus());
        log.debug(line);
    }

}
