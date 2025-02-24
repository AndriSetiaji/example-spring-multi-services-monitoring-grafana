package com.ans.alpha.user.producer;

import com.ans.alpha.user.service.User;
import com.ans.alpha.user.service.UserService;
import com.ans.common.config.RabbitMqConfig;
import io.opentelemetry.api.GlobalOpenTelemetry;
import io.opentelemetry.api.trace.*;
import io.opentelemetry.api.trace.propagation.W3CTraceContextPropagator;
import io.opentelemetry.context.Context;
import io.opentelemetry.context.Scope;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class RabbitMqProducer {
    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMqProducer.class);
    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;
    private final Tracer tracer; // OpenTelemetry Tracer

    public RabbitMqProducer(RabbitTemplate rabbitTemplate, ObjectMapper objectMapper, Tracer tracer) {
        this.rabbitTemplate = rabbitTemplate;
        this.objectMapper = objectMapper;
        this.tracer = tracer;
    }

    public void sendCreateUser(User user) {
        LOGGER.info("🚀 Sending message to RabbitMQ: {}", user);
        try{
            String payload = objectMapper.writeValueAsString(user);
            rabbitTemplate.convertAndSend(
                    RabbitMqConfig.EXCHANGE_NAME,
                    RabbitMqConfig.ROUTING_KEY,
                    payload
            );
            LOGGER.info("✔ Message sent!");
        }catch (Exception e){
            throw new RuntimeException("x Failed Message");
        }
    }
}
