package com.ans.alpha;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.ans.alpha", "com.ans.common"})
public class TestSpringAlphaSvcApplication {

	public static void main(String[] args) {
		SpringApplication.run(TestSpringAlphaSvcApplication.class, args);
	}

}
