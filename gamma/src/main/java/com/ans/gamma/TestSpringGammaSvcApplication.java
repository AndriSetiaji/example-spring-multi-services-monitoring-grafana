package com.ans.gamma;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.ans.gamma", "com.ans.common"})
public class TestSpringGammaSvcApplication {

    public static void main(String[] args) {
        SpringApplication.run(TestSpringGammaSvcApplication.class, args);
    }

}
