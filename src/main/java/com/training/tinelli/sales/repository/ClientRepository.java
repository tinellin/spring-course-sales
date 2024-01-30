package com.training.tinelli.sales.repository;

import com.training.tinelli.sales.domain.entity.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ClientRepository {

    private static final String INSERT = "INSERT INTO Client (name) VALUES (?) ";
    private static final String SELECT_ALL = "SELECT * FROM Client ";
    private static final String UPDATE = "UPDATE Client SET name = ? WHERE id = ? ";
    private static final String DELETE = "DELETE FROM Client WHERE id = ? ";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Client save(Client client){
        jdbcTemplate.update(INSERT, new Object[]{client.getName()});
        return client;
    }

    public Client update(Client client){
        jdbcTemplate.update(UPDATE, new Object[]{client.getName(), client.getId()});
        return client;
    }

    public void deletar(Client client){
        jdbcTemplate.update(DELETE, new Object[]{client.getId()});
    }

    public List<Client> getByName(String name){
        return jdbcTemplate.query(
                SELECT_ALL.concat(" WHERE name LIKE ? "),
                new Object[]{"%" + name + "%"},
                (rs, rowNum) -> new Client(rs.getInt("id"), rs.getString("name"))
        );
    }

    public List<Client> getAll() {
        return jdbcTemplate.query(SELECT_ALL, (rs, rowNum) -> new Client(rs.getInt("id"), rs.getString("name")));
    }
}
