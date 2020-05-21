package no.fint.consumer.utils;

import lombok.extern.slf4j.Slf4j;
import no.fint.consumer.exceptions.ForbiddenException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class RequestHeaderInterceptor extends HandlerInterceptorAdapter {

    private final String headerName;

    public RequestHeaderInterceptor(String headerName) {
        this.headerName = headerName;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        /*
        Dummy logic for testing purposes... implement some clever hackaton logic with request headers, uri etc.
         */

        // TODO How to distinguish between collection access and single access?  By counting number of path elements?

        String header = request.getHeader(headerName);
        final String[] permittedPaths = StringUtils.split(header, ",;");

        if (StringUtils.startsWithAny(request.getRequestURI(), permittedPaths)) {
            return true;
        }

        throw new ForbiddenException();
    }
}
