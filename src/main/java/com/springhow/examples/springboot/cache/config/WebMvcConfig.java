package com.springhow.examples.springboot.cache.config;

import com.springhow.examples.springboot.cache.interceptor.CustomTraceInterceptor;
import io.opentelemetry.api.trace.Tracer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {

    private final Tracer tracer;

    @Autowired
    public WebMvcConfig(Tracer tracer) {
        this.tracer = tracer;
    }
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new CustomTraceInterceptor());
    }
}
