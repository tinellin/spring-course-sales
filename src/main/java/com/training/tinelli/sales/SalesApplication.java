package com.training.tinelli.sales;

import com.training.tinelli.sales.domain.entity.Client;
import com.training.tinelli.sales.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SalesApplication {

	@Bean
	public CommandLineRunner init(@Autowired ClientRepository clientRepository) {
		return args -> {
			System.out.println("!********* Salvando clientes *********!");
			clientRepository.save(new Client(null, "Maria", "12345678901"));
			clientRepository.save(new Client(null, "Bob", "09876543212"));
			clientRepository.save(new Client(null, "Alexa", "11111111111"));
		};
	}

	public static void main(String[] args) {
		SpringApplication.run(SalesApplication.class, args);
	}
}
