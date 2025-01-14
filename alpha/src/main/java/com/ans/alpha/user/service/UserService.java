package com.ans.alpha.user.service;

import com.ans.alpha.user.client.BetaClient;
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
    private final BetaClient betaClient;

    public UserService(BetaClient betaClient) {
        this.betaClient = betaClient;
    }

    public String getUsers() {
        long start = System.currentTimeMillis();
        LOGGER.info("starts getUsers");
        return betaClient.getUsersClient();
    }

    public String getUsersExceptionGamma() {
        long start = System.currentTimeMillis();
        LOGGER.info("starts getUsers - gamma error");

        // call beta-svc
        try {
            return betaClient.getGammaClient();
        } catch (Exception e) {
            throw new RuntimeException("Simulated error in gamma-svc");
        }
    }

    public String getUsersExceptionBeta() {
        long start = System.currentTimeMillis();
        LOGGER.info("starts getUsers");

        // call beta-svc
        try {
            return betaClient.getBetaClient();
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
