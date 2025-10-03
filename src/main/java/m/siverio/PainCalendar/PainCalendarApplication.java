package m.siverio.paincalendar;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PainCalendarApplication {

	public static void main(String[] args) {
		SpringApplication.run(PainCalendarApplication.class, args);

		System.out.println("*******");
	}
	@PostConstruct
	public void checkEnv() {
		System.out.println("DB_URL: " + System.getenv("DB_URL"));
		System.out.println("DB_USER: " + System.getenv("DB_USER"));
		System.out.println("DB_PASSWORD: " + System.getenv("DB_PASSWORD"));
	}
}
