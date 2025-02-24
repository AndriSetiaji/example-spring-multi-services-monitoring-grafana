package com.ans.alpha.user.controller;


import com.ans.alpha.user.service.User;
import com.ans.alpha.user.service.UserService;
import io.micrometer.core.annotation.Timed;
import io.micrometer.tracing.SpanName;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/v1/users")
public class UserController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("")
    @Timed(value = "alpha-svc.UserController.getUsers", description = "getUsers")
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

    @PostMapping("/create")
    @Timed(value = "alpha-svc.UserController.createUser", description = "createUser")
    public String createUser(@RequestBody User user) {
        return userService.createUser(user);
    }
}

