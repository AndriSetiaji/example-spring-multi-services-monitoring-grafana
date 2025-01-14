package com.ans.gamma;

import com.ans.common.config.FeignConfig;
import com.ans.common.config.OpenTelemetryConfig;
import com.ans.common.config.TimedConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

@Import({OpenTelemetryConfig.class, TimedConfig.class, FeignConfig.class})
@SpringBootApplication
@ComponentScan(basePackages = {"com.ans.gamma"})
@EnableFeignClients
public class TestSpringGammaSvcApplication {
    public static void main(String[] args) {
        SpringApplication.run(TestSpringGammaSvcApplication.class, args);
    }
}
