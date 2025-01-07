package com.ans.beta.user.service;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
public class UserService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    @Value("${svc.gamma.user.endpoint}")
    private String svcGammaUserEndpoint;

    @Value("${svc.gamma.user.exception}")
    private String svcGammaUserException;

    private final RestTemplate restTemplate;

    public UserService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String getUsers() {
        long start = System.currentTimeMillis();
        LOGGER.info("beta-svc starts getUsers");

        // call gamma-svc
        String url = svcGammaUserEndpoint;
        try {
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
            LOGGER.info("total time:{}", System.currentTimeMillis() - start);
            return response.getBody();
        } catch (RestClientException e) {
            throw new RuntimeException("Failed to call external API: " + e.getMessage(), e);
        }
    }

    public String getUsersExceptionGamma() {
        long start = System.currentTimeMillis();
        LOGGER.info("beta-svc starts getUsersException");

        // call gamma-svc
        String url = svcGammaUserException;
        try {
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
            return response.getBody();
        } catch (RestClientException e) {
            throw new RuntimeException("Failed to call external API: " + e.getMessage(), e);
        }
    }

    public String getUsersExceptionBeta() {
        getUsers();
        LOGGER.error("beta-svc a test error.");
        throw new RuntimeException("Simulated error in beta-svc");
    }
}
