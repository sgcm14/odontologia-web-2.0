package clinica.sistemaReservaTurno;

import org.apache.log4j.PropertyConfigurator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SistemaReservaTurnoApplication {

	public static void main(String[] args) {
		//PropertyConfigurator.configure("log4j.properties");
		SpringApplication.run(SistemaReservaTurnoApplication.class, args);
	}

}
