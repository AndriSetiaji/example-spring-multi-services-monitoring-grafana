package com.ans.gamma.user.service;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class UserService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);
    private final UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public List<User> getUsers() {
        long start = System.currentTimeMillis();
        LOGGER.debug("gamma-svc start getUsers");
        List<User> list = userDao.getUsers();
        LOGGER.info("total time:{}", System.currentTimeMillis() - start);
        return list;
    }

    public String getUsersException() {
        LOGGER.error("gamma-svc a test error.");
        throw new RuntimeException("Simulated error in gamma-svc");
    }
}
