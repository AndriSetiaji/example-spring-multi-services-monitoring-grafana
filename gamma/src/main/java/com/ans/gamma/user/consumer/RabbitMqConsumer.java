package com.ans.gamma.user.consumer;

import com.ans.common.config.RabbitMqConfig;
import com.ans.gamma.user.persistence.domain.User;
import com.ans.gamma.user.persistence.repository.UserRepository;
import com.ans.gamma.user.service.UserDao;
import com.ans.gamma.user.service.UserService;
import io.opentelemetry.api.GlobalOpenTelemetry;
import io.opentelemetry.api.trace.*;
import io.opentelemetry.context.Context;
import io.opentelemetry.context.Scope;
import io.opentelemetry.context.propagation.TextMapGetter;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.core.Message;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.nio.charset.StandardCharsets;
import java.util.Map;

@Slf4j
@Service
public class RabbitMqConsumer {
    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMqConsumer.class);
    private final UserDao userDao;
    private final ObjectMapper objectMapper;
    private final Tracer tracer; // OpenTelemetry Tracer

    public RabbitMqConsumer(UserDao userDao, ObjectMapper objectMapper, Tracer tracer) {
        this.userDao = userDao;
        this.objectMapper = objectMapper;
        this.tracer = tracer;
    }

    @RabbitListener(queues = RabbitMqConfig.QUEUE_NAME)
    public void receiveMessage(Message message) {
        String messageBody = new String(message.getBody(), StandardCharsets.UTF_8);
        LOGGER.info("🟢 Received message from RabbitMQ");
        try{
            User user = objectMapper.readValue(messageBody, User.class);
            userDao.createUser(user);
            LOGGER.info("user created : {}", user);
        }catch (Exception e){
            throw new RuntimeException("Failed inset user");
        }

    }
}
