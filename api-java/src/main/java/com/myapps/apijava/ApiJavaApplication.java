package com.myapps.apijava;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ApiJavaApplication {

  public static void main(String[] args) {
    // TODO: A fazer alterações e testes
    /**
     *  Testar os roles e authorities nos endpoints OK
     *  Testar o acesso ao Token nos endpoints OK
     *  Manipulação de erro no securityconfig OK
     *  Por último, preparar endpoint de criação de user (email único) (codificar a senha internamente no back-end também? SIM) OK
     *  também ajustar os campos created at e updated at do user OK
     *  Ajustar controllers para não ter try-catch
     */
    SpringApplication.run(ApiJavaApplication.class, args);
  }

}
