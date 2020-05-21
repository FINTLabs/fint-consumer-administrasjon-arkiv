package no.fint.consumer.utils;

import lombok.extern.slf4j.Slf4j;
import no.fint.consumer.exceptions.ForbiddenException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

@Slf4j
public class RequestHeaderInterceptor extends HandlerInterceptorAdapter {

    private final String collectionHeaderName = "x-fint-access-collection";
    private final String readHeaderName = "x-fint-access-read";
    private final String modifyHeaderName = "x-fint-access-modify";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        /*
        Dummy logic for testing purposes... implement some clever hackaton logic with request headers, uri etc.
         */

        if (StringUtils.equalsAny(request.getMethod(), "GET", "HEAD")) {

            if (StringUtils.endsWithAny(request.getRequestURI(), RestEndpoints.ALL_ENDPOINTS)) {

                final String[] permittedPaths = StringUtils.split(request.getHeader(collectionHeaderName), ",;");

                if (StringUtils.equalsAny(request.getRequestURI(), permittedPaths)) {
                    return true;
                }

            } else {

                final String[] permittedPaths = StringUtils.split(request.getHeader(readHeaderName), ",;");

                if (StringUtils.startsWithAny(request.getRequestURI(), permittedPaths)) {
                    return true;
                }
            }

        } else {
            String[] permittedPaths = StringUtils.split(request.getHeader(modifyHeaderName), ",;");
            if (StringUtils.startsWithAny(request.getRequestURI(), permittedPaths)) {
                return true;
            }

        }
        throw new ForbiddenException();
    }
}
