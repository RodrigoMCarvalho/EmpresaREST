package com.empresaRest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan({ "com.empresaRest" }) 
@SpringBootApplication
public class EmpresaRestApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmpresaRestApplication.class, args);
	}

}
