package no.fint.consumer.config;

import no.fint.consumer.utils.RequestHeaderInterceptor;
import no.fint.consumer.utils.RestEndpoints;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {

    @Qualifier("linkMapper")
    @Autowired
    private Map<String, String> links;


    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        AntPathMatcher matcher = new AntPathMatcher();
        matcher.setCaseSensitive(false);
        configurer.setPathMatcher(matcher);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new RequestHeaderInterceptor(links.values()))
                .addPathPatterns(links.values().toArray(new String[0]))
                .excludePathPatterns(RestEndpoints.ADMIN);
    }
}
