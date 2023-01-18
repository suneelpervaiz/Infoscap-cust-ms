package tiers.app.user;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import tiers.app.user.model.Role;
import tiers.app.user.model.User;
import tiers.app.user.service.UserService;

import java.util.HashSet;

@SpringBootApplication
public class TiersAppMaverickApplication {

	public static void main(String[] args) {
		SpringApplication.run(TiersAppMaverickApplication.class, args);
	}

	@Bean
	PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}


	/*@Bean
	CommandLineRunner run(UserService userService){
		return args -> {

			userService.saveRole(new Role(null, "ROLE_USER"));
			userService.saveRole(new Role(null, "ROLE_ADMIN"));
			userService.saveRole(new Role(null, "ROLE_CUSTOMER"));
			userService.saveRole(new Role(null, "ROLE_SUPER_ADMIN"));

			userService.saveUser(new User(null, "suneel", "123", new HashSet<>(), new HashSet<>(),new HashSet<>()));
			userService.saveUser(new User(null, "adeel", "123", new HashSet<>(), new HashSet<>(),new HashSet<>()));
			userService.saveUser(new User(null, "jonatas", "123", new HashSet<>(), new HashSet<>(),new HashSet<>()));
			userService.saveUser(new User(null, "gill", "123", new HashSet<>(), new HashSet<>(),new HashSet<>()));
			userService.saveUser(new User(null, "salma", "123", new HashSet<>(), new HashSet<>(),new HashSet<>()));

			userService.assignRoleToUser("suneel", "ROLE_USER");
			userService.assignRoleToUser("adeel", "ROLE_ADMIN");
			userService.assignRoleToUser("gill", "ROLE_CUSTOMER");

		};
	}
*/

}
