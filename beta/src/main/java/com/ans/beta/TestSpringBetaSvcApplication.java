package com.ans.beta;

import com.ans.common.config.FeignConfig;
import com.ans.common.config.OpenTelemetryConfig;
import com.ans.common.config.RestTemplateConfig;
import com.ans.common.config.TimedConfig;
//import id.co.bjj.core.component.config.CoreConfig;
import org.springframework.boot.SpringApplication;
//import id.co.bjj.core.component.config.RestTemplateConfig;
//import id.co.bjj.core.component.config.TimedConfig;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Import;


//@Import({CoreConfig.class, TimedConfig.class, RestTemplateConfig.class})
@Import({FeignConfig.class, OpenTelemetryConfig.class, RestTemplateConfig.class, TimedConfig.class})
@SpringBootApplication
@EnableFeignClients
public class TestSpringBetaSvcApplication {

    public static void main(String[] args) {
        SpringApplication.run(TestSpringBetaSvcApplication.class, args);
    }

}
