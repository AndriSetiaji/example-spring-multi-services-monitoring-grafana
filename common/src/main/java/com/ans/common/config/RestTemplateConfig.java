package com.ans.common.config;

import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.SpanKind;
import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.context.Scope;
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
    @Autowired
    private Tracer tracer;

    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getInterceptors().add(new TraceContextInterceptor(tracer));
        return restTemplate;
    }

    static class TraceContextInterceptor implements ClientHttpRequestInterceptor {

        private final Tracer tracer;

        public TraceContextInterceptor(Tracer tracer) {
            this.tracer = tracer;
        }

        @Override
        public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
            Span span = tracer.spanBuilder("outgoing-http-call").setSpanKind(SpanKind.CLIENT).startSpan();
            try (Scope scope = span.makeCurrent()) {
                // Ambil trace ID, span ID, dan flag
                String traceId = span.getSpanContext().getTraceId();
                String spanId = span.getSpanContext().getSpanId();
                String traceFlags = span.getSpanContext().isSampled() ? "01" : "00"; // 01 untuk traced, 00 untuk tidak

                // Format traceparent sesuai spesifikasi W3C
                String traceParent = String.format("00-%s-%s-%s", traceId, spanId, traceFlags);

                // Tambahkan traceparent ke header
                request.getHeaders().add("traceparent", traceParent);
            } finally {
                span.end();
            }
            return execution.execute(request, body);
        }
    }
}

