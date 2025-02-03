package com.ans.alpha.config;

import feign.Client;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.SpanKind;
import io.opentelemetry.api.trace.Tracer;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TestFeignConfig {

    @Bean(name = "requestInterceptorTest")
    public RequestInterceptor requestInterceptorTest() {
        return new RequestInterceptor() {
            @Override
            @Before("execution(* feign.Client.*(..)) && args(target, requestTemplate)")
            public void apply(RequestTemplate template) {
                template.header("test", "test");
            }
        };
    }
}
