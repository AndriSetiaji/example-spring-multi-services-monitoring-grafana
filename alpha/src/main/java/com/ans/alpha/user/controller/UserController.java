package com.ans.alpha.user.controller;


import com.ans.alpha.user.service.User;
import com.ans.alpha.user.service.UserService;
import io.micrometer.tracing.SpanName;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/v1/users")
public class UserController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;

    private UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("")
    public String getUsers() {
        return userService.getUsers();
    }

    @GetMapping("/exception/gamma")
    public String getUsersExceptionGamma() {
        return userService.getUsersExceptionGamma();
    }

    @GetMapping("/exception/beta")
    public String getUsersExceptionBeta() {
        return userService.getUsersExceptionBeta();
    }

    @GetMapping("/exception/alpha")
    public String getUsersExceptionAlpha() {
        return userService.getUsersExceptionAlpha();
    }
}

