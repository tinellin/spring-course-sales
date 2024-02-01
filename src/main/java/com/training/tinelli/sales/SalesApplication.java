package com.training.tinelli.sales;

import com.training.tinelli.sales.domain.entity.Client;
import com.training.tinelli.sales.domain.entity.Product;
import com.training.tinelli.sales.domain.repository.ClientRepository;
import com.training.tinelli.sales.domain.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;

@SpringBootApplication
public class SalesApplication {

	@Bean
	public CommandLineRunner init(@Autowired ClientRepository clientRepository, @Autowired ProductRepository productRepository) {
		return args -> {
			System.out.println("!********* Salvando clientes *********!");
			clientRepository.save(new Client(null, "Maria", "12345678901"));
			clientRepository.save(new Client(null, "Bob", "09876543212"));
			clientRepository.save(new Client(null, "Alexa", "11111111111"));

			productRepository.save(new Product("Iphone 15", new BigDecimal("5000.00")));
			productRepository.save(new Product("Notebook Dell", new BigDecimal("5500.00")));
			productRepository.save(new Product("Tv Samsung", new BigDecimal("3200.00")));
		};
	}

	public static void main(String[] args) {
		SpringApplication.run(SalesApplication.class, args);
	}
}
