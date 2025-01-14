package com.ans.gamma.user.controller;

import com.ans.gamma.user.persistence.domain.User;
import com.ans.gamma.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/v3/users")
public class UserController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;

    private UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("")
    public User getUsers(@RequestHeader Map<String, String> headers) {
        LOGGER.info("gamma-svc start");
        return userService.getUserByIdAndName();
    }

    @GetMapping(value = "/exception")
    public String throwError(@RequestHeader Map<String, String> headers) {
        LOGGER.info("gamma-svc exception");
        return userService.getUsersException();
    }

   @GetMapping(value = "/specific")
    public User getUserById1(@RequestHeader Map<String, String> headers) {
        LOGGER.info("gamma-svc exception");
        return userService.getUserByIdAndName();
    }
}
