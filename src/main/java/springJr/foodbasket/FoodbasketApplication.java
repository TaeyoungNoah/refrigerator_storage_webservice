package springJr.foodbasket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class FoodbasketApplication {

	public static void main(String[] args) {
		SpringApplication.run(FoodbasketApplication.class, args);
	}

}
