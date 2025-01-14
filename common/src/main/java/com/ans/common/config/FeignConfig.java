package com.ans.common.config;

import feign.Client;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.SpanKind;
import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.context.Scope;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfig {

    @Autowired
    private Tracer tracer;

    @Bean
    public Client feignClient() {
        return new Client.Default(null, null);
    }
    @Bean
    public RequestInterceptor requestInterceptor() {
        return new RequestInterceptor() {
            @Override
            @Before("execution(* feign.Client.*(..)) && args(target, requestTemplate)")
            public void apply(RequestTemplate template) {

                Span span = tracer.spanBuilder("outgoing-http-call").setSpanKind(SpanKind.CLIENT).startSpan();

                try (Scope scope = span.makeCurrent()) {
                    // Ambil trace ID, span ID, dan flag
                    String traceId = span.getSpanContext().getTraceId();
                    String spanId = span.getSpanContext().getSpanId();
                    String traceFlags = span.getSpanContext().isSampled() ? "01" : "00"; // 01 untuk traced, 00 untuk tidak

                    // Format traceparent sesuai spesifikasi W3C
                    String traceParent = String.format("00-%s-%s-%s", traceId, spanId, traceFlags);

                    // Tambahkan traceparent ke header
                    template.header("traceparent", traceParent);
                } finally {
                    span.end();
                }
            }
        };
    }
}
