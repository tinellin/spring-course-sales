package com.training.tinelli.sales.repository;

import com.training.tinelli.sales.domain.entity.Client;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class ClientRepository {
    @Autowired
    private EntityManager entityManager;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Transactional
    public Client save(Client client){
        entityManager.persist(client);
        return client;
    }

    @Transactional
    public Client update(Client client){
        entityManager.merge(client);
        return client;
    }

    @Transactional
    public void delete(Client client){
        entityManager.remove(client);
    }

    @Transactional
    public void delete(Integer id){
        Client client = entityManager.find(Client.class, id);
        delete(client);
    }

    @Transactional(readOnly = true)
    public List<Client> getByName(String name){
        TypedQuery<Client> query = entityManager.createQuery("SELECT c FROM Client c WHERE c.name like :name", Client.class);
        query.setParameter("name", "%" + name + "%");
        return query.getResultList();
    }

    @Transactional(readOnly = true)
    public List<Client> getAll() {
        return entityManager.createQuery("FROM Client", Client.class).getResultList();
    }
}
