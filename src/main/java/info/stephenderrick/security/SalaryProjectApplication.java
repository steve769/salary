package info.stephenderrick.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class SalaryProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(SalaryProjectApplication.class, args);
	}

}
