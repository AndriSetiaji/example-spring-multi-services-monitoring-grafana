package com.ans.common.config;

import com.ans.common.handler.LogObservationHandler;
import feign.*;
import feign.codec.Decoder;
import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.SpanKind;
import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.context.Scope;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Configuration
public class FeignConfig {

    private static final Logger log  = LoggerFactory.getLogger(FeignConfig.class);

    @Autowired
    private Tracer tracer;

    @Bean
    public Client feignClient() {
        return new Client.Default(null, null);
    }

    @Bean
    public RequestInterceptor requestInterceptorBefore() {
        return new RequestInterceptor() {
            @Override
            @Before("execution(* feign.Client.*(..)) && args(target, requestTemplate)")
            public void apply(RequestTemplate template) {

                Span span = tracer.spanBuilder("outgoing-http-call-common-lib").setSpanKind(SpanKind.CLIENT).startSpan();

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

    @Bean
    public RequestInterceptor requestInterceptorLog() {
        return template -> {
            log.info("Feign Request URL: {}", template.url());
            log.info("Feign Request Method: {}", template.method());
            log.info("Feign Request Headers: {}", template.headers());
            log.info("Feign Request body: {}", template.body());

            // Cek jika body ada
            if (template.body() != null) {
                log.info("Feign Request Body: {}", new String(template.body(), StandardCharsets.UTF_8));
            } else {
                log.info("Feign Request Body: [EMPTY]");
            }
        };
    }

    @Bean
    public Decoder feignDecoder() {
        return (response, type) -> {
            String responseBody = "[EMPTY]";
            byte[] bodyBytes = null;

            if (response.body() != null) {
                try {
                    // Simpan body ke dalam byte array agar bisa dibaca ulang
                    bodyBytes = response.body().asInputStream().readAllBytes();
                    responseBody = new String(bodyBytes, StandardCharsets.UTF_8);
                } catch (IOException e) {
                    log.error("Error reading Feign response body", e);
                }
            }

            log.info("Feign Response Status: {}", response.status());
            log.info("Feign Response Headers: {}", response.headers());
            log.info("Feign Response Body: {}", responseBody);

            // Buat ulang response body agar tidak hilang
            Response modifiedResponse = response.toBuilder()
                    .body(bodyBytes != null ? new ByteArrayInputStream(bodyBytes) : null, bodyBytes != null ? bodyBytes.length : 0)
                    .build();

            // Decode menggunakan decoder default agar Feign bisa membaca ulang body
            return new feign.codec.StringDecoder().decode(modifiedResponse, type);
        };
    }
}
