package com.training.tinelli.sales;

import com.training.tinelli.sales.domain.entity.Client;
import com.training.tinelli.sales.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class SalesApplication {

	@Bean
	public CommandLineRunner init(@Autowired ClientRepository clientRepository) {
		return args -> {
			System.out.println("!********* Salvando clientes *********!");
			clientRepository.save(new Client("Maria"));
			clientRepository.save(new Client("Bob"));
			clientRepository.save(new Client("Alexa"));

			List<Client> allClients = clientRepository.getAll();
			allClients.forEach(System.out::println);

			System.out.println("!********* Atualizando clientes *********!");
			allClients.forEach(c -> {
				c.setName(c.getName() + " Doe");
				clientRepository.update(c);
			});

			allClients = clientRepository.getAll();
			allClients.forEach(System.out::println);

			System.out.println("!********* Buscando clientes *********!");
			clientRepository.getByName("a").forEach(System.out::println);

			System.out.println("!********* Deletando clientes *********!");
			allClients.forEach(clientRepository::delete);

			allClients = clientRepository.getAll();
			if (allClients.isEmpty()) System.out.println("Not found.");
		};
	}

	public static void main(String[] args) {
		SpringApplication.run(SalesApplication.class, args);
	}
}
