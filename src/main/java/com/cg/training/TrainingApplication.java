package com.cg.training;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = { "com.cg.training" })
@EntityScan
@EnableJpaRepositories
public class TrainingApplication {
	private static final Logger logger = LoggerFactory.getLogger(TrainingApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(TrainingApplication.class, args);
		logger.info("\n....................................................."
				+ "\n.................Application started................."
				+ "\n.....................................................\n");

	}

}
