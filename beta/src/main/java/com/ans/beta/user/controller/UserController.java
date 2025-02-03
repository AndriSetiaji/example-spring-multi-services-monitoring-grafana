package com.ans.beta.user.controller;

import com.ans.beta.user.service.UserService;
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
@RequestMapping("/v2/users")
public class UserController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;

    private UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("")
    public String getUsers(@RequestHeader Map<String, String> headers) {
        LOGGER.info("beta-svc - test log get user controller");
        // debug header
//        headers.forEach((key, value) -> LOGGER.info("Header {} = {}", key, value));
        return userService.getUsers();
    }

    @GetMapping(value = "/exception/gamma")
    public String getUsersGamma(@RequestHeader Map<String, String> headers) {
        LOGGER.info("beta-svc - test log get user exception gamma controller");
        return userService.getUsersExceptionGamma();
    }

    @GetMapping(value = "/exception/beta")
    public String getUsersBeta(@RequestHeader Map<String, String> headers) {
        LOGGER.info("beta - test log get user exception beta controller");
        return userService.getUsersExceptionBeta();
    }
}
