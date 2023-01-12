package alex.toy.nmj;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class NmjApplication {

	public static void main(String[] args) {
		SpringApplication.run(NmjApplication.class, args);
	}
}
