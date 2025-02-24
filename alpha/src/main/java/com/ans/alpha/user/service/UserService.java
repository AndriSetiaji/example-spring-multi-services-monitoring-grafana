package com.ans.alpha.user.service;

import com.ans.alpha.user.client.BetaClient;
import com.ans.alpha.user.producer.RabbitMqProducer;
import com.ans.common.config.RabbitMqConfig;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.micrometer.tracing.SpanName;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
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
    private final RabbitMqProducer rabbitMqProducer;

    public UserService(BetaClient betaClient, RabbitMqProducer rabbitMqProducer) {
        this.betaClient = betaClient;
        this.rabbitMqProducer = rabbitMqProducer;
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

    public String createUser(User user) {
        try {
            rabbitMqProducer.sendCreateUser(user);
            return "success";
        } catch (Exception e) {
            throw new RuntimeException("Error Create User");
        }
    }
}
