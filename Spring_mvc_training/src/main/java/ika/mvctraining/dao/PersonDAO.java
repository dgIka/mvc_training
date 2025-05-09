package ika.mvctraining.dao;

import ika.mvctraining.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class PersonDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public List<Person> index() {
        return jdbcTemplate.query("SELECT * FROM Person", new BeanPropertyRowMapper<>(Person.class));
    }

    public Person show(int id) {
        return jdbcTemplate.queryForObject("SELECT * FROM Person WHERE id = ?", new Object[] { id }, new BeanPropertyRowMapper<>(Person.class));
    }

    public void save(Person person) {
        jdbcTemplate.update("INSERT INTO person VALUES (1, ?, ?, ?)", person.getName(), person.getAge(), person.getEmail());
    }
    public void update(int id, Person updatedPerson) {
    jdbcTemplate.update("UPDATE person SET name = ?, age = ?, email = ? WHERE id = ?", updatedPerson.getName(), updatedPerson.getAge(), updatedPerson.getEmail(), id);
    }

    public void delete(int id) {
            jdbcTemplate.update("DELETE FROM person WHERE id = ?", id);
        }


}
