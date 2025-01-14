package com.ans.beta.user.service;

import com.ans.beta.user.client.GammaClient;
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

    private final GammaClient gammaClient;

    public UserService(GammaClient gammaClient) {
        this.gammaClient = gammaClient;
    }

    public String getUsers() {
        LOGGER.info("beta-svc starts getUsers");
        return gammaClient.getUsersClient();
    }

    public String getUsersExceptionGamma() {
        long start = System.currentTimeMillis();
        return gammaClient.getGammaExceptionClient();
    }

    public String getUsersExceptionBeta() {
        getUsers();
        LOGGER.error("beta-svc a test error.");
        throw new RuntimeException("Simulated error in beta-svc");
    }
}
