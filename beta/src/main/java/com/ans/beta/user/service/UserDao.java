package com.ans.beta.user.service;


import com.ans.beta.user.util.UserConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class UserDao {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserDao.class);
    private final UserConverter userConverter;

    public UserDao(UserConverter userConverter) {
        this.userConverter = userConverter;
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
                .mapToObj(i -> userConverter.createUser(i, "name user ke-" + i))
                .collect(Collectors.toList());
    }
}
