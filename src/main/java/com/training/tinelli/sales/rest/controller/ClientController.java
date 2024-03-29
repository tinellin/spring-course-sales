package com.training.tinelli.sales.rest.controller;

import com.training.tinelli.sales.domain.entity.Client;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.training.tinelli.sales.domain.repository.ClientRepository;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/v0/clients")
@Api("API de Clientes")
public class ClientController {
    @Autowired
    private ClientRepository clientRepo;

    @ApiOperation("Obter detalhes de um cliente")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Cliente encontrado com sucesso."),
            @ApiResponse(code = 404, message = "Cliente não encontrado.")
    })
    @GetMapping("/{id}")
    public Client getClientById(@PathVariable @ApiParam("Id do cliente") Integer id) {
        return clientRepo
                .findById(id)
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado."));
    }

    @ApiOperation("Salva um novo cliente")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Cliente salvo com sucesso."),
            @ApiResponse(code = 400, message = "Erro de validação.")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Client saveClient(@RequestBody @Valid Client client) {
        return clientRepo.save(client);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Client deleteClient(@PathVariable Integer id) {
        return clientRepo.findById(id)
                .map(client -> {
                    clientRepo.delete(client);
                    return client;
                })
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado.")
                );
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Client updateClient (@PathVariable Integer id, @RequestBody @Valid Client client) {
        return clientRepo.findById(id).map(isClientExist -> {
            client.setId(isClientExist.getId());
            clientRepo.save(client);
            return isClientExist;
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado."));
    }

    @GetMapping
    public List<Client> findClient(Client filter) {
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

        Example<Client> example = Example.of(filter, matcher);
        return clientRepo.findAll(example);
    }
}
