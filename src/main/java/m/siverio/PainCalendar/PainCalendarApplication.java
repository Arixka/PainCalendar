package m.siverio.paincalendar;

import lombok.extern.slf4j.Slf4j;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class PainCalendarApplication {

	public static void main(String[] args) {
		SpringApplication.run(PainCalendarApplication.class, args);
		log.info("******* Inicio de PainCalendarApplication *******");
	}
}
