package com.ans.alpha.user.service;

import io.micrometer.tracing.SpanName;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Slf4j
@Service
public class UserService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    @Value("${svc.beta.user.endpoint}")
    private String svcBetaUserEndpoint;

    @Value("${svc.beta.user.exception.beta}")
    private String svcBetaUserExceptionBeta;

    @Value("${svc.beta.user.exception.gamma}")
    private String svcBetaUserExceptionGamma;

    private final RestTemplate restTemplate;

    public UserService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String getUsers() {
        long start = System.currentTimeMillis();
        LOGGER.info("starts getUsers");

        // call beta-svc
        String url = svcBetaUserEndpoint;
        try {
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
            return response.getBody();
        } catch (Exception e) {
            throw new RuntimeException("Simulated error in gamma-svc");
        }
    }

    public String getUsersExceptionGamma() {
        long start = System.currentTimeMillis();
        LOGGER.info("starts getUsers - gamma error");

        // call beta-svc
        String url = svcBetaUserExceptionGamma;
        try {
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
            return response.getBody();
        } catch (Exception e) {
            throw new RuntimeException("Simulated error in gamma-svc");
        }
    }

    public String getUsersExceptionBeta() {
        long start = System.currentTimeMillis();
        LOGGER.info("starts getUsers");

        // call beta-svc
        String url = svcBetaUserExceptionBeta;
        try {
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
            LOGGER.info("total time:{}", System.currentTimeMillis() - start);
            return response.getBody();
        } catch (RestClientException e) {
            throw new RuntimeException("Failed to call external API: " + e.getMessage(), e);
        }
    }

    public String getUsersExceptionAlpha() {
        getUsers();
        LOGGER.error("alpha-svc a test error.");
        throw new RuntimeException("Simulated error in alpha-svc");
    }
}
