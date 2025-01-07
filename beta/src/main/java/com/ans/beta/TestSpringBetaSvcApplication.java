package com.ans.beta;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.ans.beta", "com.ans.common"})
public class TestSpringBetaSvcApplication {

	public static void main(String[] args) {
		SpringApplication.run(TestSpringBetaSvcApplication.class, args);
	}

}
