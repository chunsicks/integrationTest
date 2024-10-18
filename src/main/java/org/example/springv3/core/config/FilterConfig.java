package org.example.springv3.core.config;

import jakarta.servlet.FilterRegistration;
import org.example.springv3.core.filter.CorsFilter;
import org.example.springv3.core.filter.JwtAuthorizationFilter;
import org.example.springv3.user.User;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Controller, @RestController, @Service, @Repository, @Component, @Configuration
 */

@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<?> corsFilter() {
        FilterRegistrationBean<CorsFilter> bean
                = new FilterRegistrationBean<>(new CorsFilter());
        //언제 동작하게 할것인지 정해야 한다
        bean.addUrlPatterns("/*");
        bean.setOrder(0);
        return bean;
    }
    //기존거
    @Bean
    public FilterRegistrationBean<?> jwtAuthorizationFilter(){
        FilterRegistrationBean<JwtAuthorizationFilter> bean
                = new FilterRegistrationBean<>(new JwtAuthorizationFilter());
        bean.addUrlPatterns("/api/*");
        bean.setOrder(0);
        return bean;
    }
}
