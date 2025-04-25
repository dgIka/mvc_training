package ika.mvctraining.dao;

import ika.mvctraining.models.Person;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PersonMapper implements RowMapper<Person> {

    @Override
    public Person mapRow(ResultSet result, int rowNum) throws SQLException {
        Person person = new Person();
        person.setId(result.getInt("id"));
        person.setName(result.getString("name"));
        person.setAge(result.getInt("age"));
        person.setEmail(result.getString("email"));
        return person;
    }
}
