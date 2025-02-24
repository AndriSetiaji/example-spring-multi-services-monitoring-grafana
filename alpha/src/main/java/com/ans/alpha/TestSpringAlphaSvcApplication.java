package com.ans.alpha;

import com.ans.common.config.*;
//import id.co.bjj.core.component.config.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Import;

//@Import({CoreConfig.class, TimedConfig.class, RestTemplateConfig.class})
@Import({FeignConfig.class, OpenTelemetryConfig.class, RestTemplateConfig.class,
		TimedConfig.class, RabbitMqConfig.class})
@SpringBootApplication
@EnableFeignClients
public class TestSpringAlphaSvcApplication {
	public static void main(String[] args) {
		SpringApplication.run(TestSpringAlphaSvcApplication.class, args);
	}
}
