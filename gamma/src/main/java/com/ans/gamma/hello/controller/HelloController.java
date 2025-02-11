package com.ans.gamma.hello.controller;

import io.micrometer.core.annotation.Timed;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/hello")
public class HelloController {
    private static final Logger LOGGER = LoggerFactory.getLogger(HelloController.class);

    @GetMapping("")
    @Timed(value = "hello.gamma", description = "hello")
    public String hello(HttpServletResponse response) {
        response.setHeader("test", "test");
        String res = "hello from gamma-svc";
        LOGGER.info(res);
        return res;
    }
}
