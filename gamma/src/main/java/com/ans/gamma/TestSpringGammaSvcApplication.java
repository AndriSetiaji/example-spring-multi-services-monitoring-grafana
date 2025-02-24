package com.ans.gamma;

import com.ans.common.config.FeignConfig;
import com.ans.common.config.OpenTelemetryConfig;
import com.ans.common.config.RestTemplateConfig;
import com.ans.common.config.TimedConfig;
//import id.co.bjj.core.component.config.RestTemplateConfig;
//import id.co.bjj.core.component.config.TimedConfig;
//import id.co.bjj.core.component.config.CoreConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Import;



//@Import({CoreConfig.class, TimedConfig.class, RestTemplateConfig.class})
@Import({FeignConfig.class, OpenTelemetryConfig.class, RestTemplateConfig.class, TimedConfig.class})
@SpringBootApplication
@EnableFeignClients
public class TestSpringGammaSvcApplication {
    public static void main(String[] args) {

        SpringApplication.run(TestSpringGammaSvcApplication.class, args);
    }
}
