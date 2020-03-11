package com.empresaRest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@ComponentScan({ "com.empresaRest" }) 
@SpringBootApplication
@EnableJpaRepositories(enableDefaultTransactions = false)
public class EmpresaRestApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmpresaRestApplication.class, args);
	}

}
