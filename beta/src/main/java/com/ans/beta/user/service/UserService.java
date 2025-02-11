package com.ans.beta.user.service;

import com.ans.beta.user.client.GammaClient;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class UserService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    private final GammaClient gammaClient;
    private final RestTemplate restTemplate;

    public UserService(
            GammaClient gammaClient
    ,RestTemplate restTemplate
    ) {
        this.gammaClient = gammaClient;
        this.restTemplate = restTemplate;
    }

    public String getUsers() {
        LOGGER.info("beta-svc starts getUsers");
        return gammaClient.getUsersClient();
    }

    public String getUsersByRestTemplate() {
        LOGGER.info("beta-svc starts getUsers");
        String url = "http://localhost:8083/v3/users";

        // Membuat header
        HttpHeaders headers = new HttpHeaders();

        // Mengirim request
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(null, headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

        return response.getBody();
//        return "";
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
