package com.ans.gamma.user.service;

import com.ans.common.config.RabbitMqConfig;
import com.ans.gamma.user.persistence.domain.User;
import com.ans.gamma.user.persistence.repository.UserRepository;
import com.ans.gamma.user.util.UserConverter;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
@Service
public class UserDao {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserDao.class);
    private final UserConverter userConverter;
    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;

    public UserDao(
            UserConverter userConverter,
            UserRepository userRepository,
            ObjectMapper objectMapper
    ) {
        this.userConverter = userConverter;
        this.userRepository = userRepository;
        this.objectMapper = objectMapper;
    }

    private static void sleepDelay(int i) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            LOGGER.info(e.toString());
        }

    }

    public List<User> getUsers() {
        Random rand = new Random();
        return IntStream.rangeClosed(1, rand.nextInt(5))
                .peek(UserDao::sleepDelay)
                .peek(i -> LOGGER.info("output data :{}", i))
                .mapToObj(i -> userConverter.createUser((long) i, "name user ke-" + i))
                .collect(Collectors.toList());
    }

    public List<User> getUsersV2() {
        List<User> list = userRepository.findAll();
        return list;
    }

    public User getUsersById1() {
        Optional<User> modelOpt = userRepository.findById(1L);
        return modelOpt.orElseGet(User::new);
    }

    public User getUsersByIdAndName(Long id, String name) {
        Optional<User> modelOpt = userRepository.findByIdAndName(id, name);
        return modelOpt.orElseGet(User::new);
    }

    public User createUser(User user) {
        User userUpdate = userRepository.save(user);
        LOGGER.info("✔ Data berhasil disimpan: " + user.getName() + ", " + user.getAddress());
        return userUpdate;
    }
}
