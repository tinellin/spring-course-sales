package com.training.tinelli.sales.rest.controller;

import com.training.tinelli.sales.domain.entity.Product;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.training.tinelli.sales.domain.repository.ProductRepository;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController()
@RequestMapping("api/v0/products")
public class ProductController {

    @Autowired
    private ProductRepository productRepo;

    @GetMapping("/{id}")
    public Product getProductById(@PathVariable Integer id) {
        return productRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado."));
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public Product saveProduct(@RequestBody @Valid Product product) {
        return productRepo.save(product);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Product updateProduct(@PathVariable Integer id, @RequestBody Product product) {
        return productRepo.findById(id).map(isProductExist -> {
            product.setId(isProductExist.getId());
            productRepo.save(product);
            return isProductExist;
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado."));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Class<Void> deleteProduct(@PathVariable Integer id) {
        return productRepo.findById(id).map(product -> {
            productRepo.delete(product);
            return Void.TYPE;
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado."));
    }

    @GetMapping
    public List<Product> findProduct(Product filter) {
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

        Example<Product> example = Example.of(filter, matcher);
        return productRepo.findAll(example);
    }
}
