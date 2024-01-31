package com.training.tinelli.sales.rest.controller;

import com.training.tinelli.sales.domain.entity.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import com.training.tinelli.sales.repository.ClientRepository;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Optional;

@Controller
@RequestMapping("api/v0/clients")
public class ClientController {
    // @GetMapping(value = "/hello/{name}",
            // define o tipo de dados que a api vai receber do cliente no corpo da requisição
            // consumes = {"application/json", "application/xml"},
            // define o tipo de dados que a api vai enviar para o cliente no corpo da requisição (o cliente decide qual vai escolher)
            // produces = {"application/json", "application/xml"}
    // ) // endpoint
    /* POR PADRÃO O SPRING JA TRABALHA COM JSON */
    /* indicando que vai haver uma resposta no corpo da requisição (escrever no notion dps sobre as anotações mais importantes dessa parte)*/
    // @ResponseBody
    //public String helloClient(@PathVariable("name") String clientName) {
    //    return String.format("Hello %s", clientName);
    //}

    @Autowired
    private ClientRepository clientRepo;

    @GetMapping("/{id}")
    @ResponseBody()
    public ResponseEntity getClientById(@PathVariable Integer id) {
        Optional<Client> client = clientRepo.findById(id);

        if (client.isEmpty())
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(client.get());
    }
}
