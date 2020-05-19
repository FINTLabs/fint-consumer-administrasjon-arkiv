package no.fint.consumer.utils;

import lombok.extern.slf4j.Slf4j;
import no.fint.consumer.exceptions.ForbiddenException;
import org.springframework.http.HttpMethod;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;
import java.util.Set;

@Slf4j
public class RequestHeaderInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        /*
        Dummy logic for testing purposes... implement some clever hackaton logic with request headers, uri etc.
         */

        String header = request.getHeader("x-fint-access-collection");
        Set<String> values = StringUtils.commaDelimitedListToSet(header);

        if (values.contains(request.getRequestURI())) {
            return true;
        }

        throw new ForbiddenException();
    }
}
