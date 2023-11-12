package org.develop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class FunkoSpringPracticaApplication {

	public static void main(String[] args) {
		SpringApplication.run(FunkoSpringPracticaApplication.class, args);
	}

}
