package pl.wasko.todoapp.controller;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Set;
@AllArgsConstructor
@Configuration
public class MvcConfiguration implements WebMvcConfigurer {
    private final Set<HandlerInterceptor> interceptors;


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        interceptors.stream().forEach(registry::addInterceptor);
    }
}
