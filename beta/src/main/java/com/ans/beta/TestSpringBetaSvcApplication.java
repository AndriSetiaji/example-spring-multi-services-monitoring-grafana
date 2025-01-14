package com.ans.beta;

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
@EnableFeignClients
public class TestSpringBetaSvcApplication {

	public static void main(String[] args) {
		SpringApplication.run(TestSpringBetaSvcApplication.class, args);
	}

}
