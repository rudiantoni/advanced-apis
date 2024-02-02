package com.myapps.apijava;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ApiJavaApplication {

	public static void main(String[] args) {
		/**
		 *    TODO:
		 *     // Testar os roles e authorities nos endpoints
		 *     // Testar o acesso ao Token nos endpoints OK
		 *     // Manipulação de erro no securityconfig
		 *     // Por último, preparar endpoint de criação de user (email único)
		 *     // também ajustar os campos created at e updated at do user
		 *     // Ajustar controllers para não ter try-catch
		 */

		SpringApplication.run(ApiJavaApplication.class, args);
	}

}
