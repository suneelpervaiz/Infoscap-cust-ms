package tiers.app.customer;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import tiers.app.customer.model.Role;
import tiers.app.customer.service.CountryService;
import tiers.app.customer.service.UserService;

@SpringBootApplication
public class TiersAppMaverickApplication {

	public static void main(String[] args) {
		SpringApplication.run(TiersAppMaverickApplication.class, args);
	}

	@Bean
	PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}


	@Bean
	CommandLineRunner run(CountryService countryService, UserService userService){
		return args -> {

			userService.saveRole(new Role(null, "CUSTOMER"));
			userService.saveRole(new Role(null, "BANK"));
			userService.saveRole(new Role(null, "DOCUMENTS_APPROVER"));


		};
	}


}
