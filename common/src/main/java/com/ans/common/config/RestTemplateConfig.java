package com.ans.common.config;

import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.SpanKind;
import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.context.Scope;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@Configuration
public class RestTemplateConfig {
    private static final Logger log = LoggerFactory.getLogger(RestTemplateConfig.class);

    @Autowired
    private Tracer tracer;

    @Bean
    public RestTemplate restTemplate(ClientHttpRequestInterceptor requestInterceptorBefore) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getInterceptors().add(requestInterceptorBefore);
        return restTemplate;
    }

    @Bean
    public ClientHttpRequestInterceptor requestInterceptorBefore() {
        return new ClientHttpRequestInterceptor() {
            @Override
            public ClientHttpResponse intercept(HttpRequest request, byte[] body,
                                                ClientHttpRequestExecution execution) throws IOException {
                Span span = tracer.spanBuilder("rest-template-http-call").setSpanKind(SpanKind.CLIENT).startSpan();
                try (Scope scope = span.makeCurrent()) {
                    // Ambil trace ID, span ID, dan flag
                    String traceId = span.getSpanContext().getTraceId();
                    String spanId = span.getSpanContext().getSpanId();

                    // 01 untuk traced, 00 untuk tidak
                    String traceFlags = span.getSpanContext().isSampled() ? "01" : "00";

                    // Format traceparent sesuai spesifikasi W3C
                    String traceParent = String.format("00-%s-%s-%s", traceId, spanId, traceFlags);

                    // Tambahkan traceparent ke header
                    request.getHeaders().add("traceparent", traceParent);
                } finally {
                    span.end();
                }
                log.info(request.getHeaders().toString());
//                log.info(body.toString());
                return execution.execute(request, body);
            }
        };
    }
}
