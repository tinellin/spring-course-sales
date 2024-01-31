package com.training.tinelli.sales.rest.controller;

import com.training.tinelli.sales.domain.entity.Client;
import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.training.tinelli.sales.repository.ClientRepository;

import java.util.List;
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

    @PostMapping
    @ResponseBody
    public ResponseEntity save(@RequestBody Client client) {
        Client saveClient = clientRepo.save(client);
        return ResponseEntity.ok(saveClient);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete (@PathVariable Integer id) {
        Optional<Client> client = clientRepo.findById(id);

        if (client.isEmpty())
            return ResponseEntity.notFound().build();

        clientRepo.delete(client.get());
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity update (@PathVariable Integer id, @RequestBody Client client) {
        return clientRepo.findById(id).map(isClientExist -> {
            client.setId(isClientExist.getId());
            clientRepo.save(client);
            return ResponseEntity.noContent().build();
        }).orElseGet(() -> ResponseEntity.notFound().build());

        /*
        * anotações para passar pro notion dps
        * o map serve p/ realizar algo se o cliente existir
        * na lógica do put devemos fazer o seguinte:
        * se o cliente existir na base com o id especificado, iremos:
        * colocar o id no cliente de att. vindo no corpo da req.
        * e salvar na base (atualizando)
        * se não encontrar existe o método orElseGet para retornar uma resposta de não encontrado
        * orElse: retorna um valor, e.g. orElse("oi")
        * orElseGet: retorna um Supplier a ser retornado: orElseGet(() -> "ola")
        *  */
    }

    /* o matcher facilita o uso de filtros de busca, uma vez que
    * flexibiliza o uso dos filtros: esse objeto permite que
    * filtros como cpf, nome possam ser usados no mesmo código
    * sendo assim, qualquer string vai ser aceita no filtro
    *  */
    @GetMapping
    public ResponseEntity find(Client filtro) {
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

        Example example = Example.of(filtro, matcher);
        List<Client> clients = clientRepo.findAll(example);

        return ResponseEntity.ok(clients);
    }
}
