package app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AeserverApplication {

	public static void main(String[] args) {
		SpringApplication.run(AeserverApplication.class, args);
		System.out.println("initialize...");
	}

}
