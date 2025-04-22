package ika.mvctraining.dao;

import ika.mvctraining.models.Person;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class PersonDAO {
    private static int PEOPLE_COUNT;

    private static final String URL = "jdbc:postgresql://localhost:5432/first_db";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "ImodiumExpress1";

    private static Connection connection;

    static {
        try {Class.forName("org.postgresql.Driver");
    } catch (ClassNotFoundException e) {
        e.printStackTrace();
    }
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Person> index() {
        List<Person> persons = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            String SQL = "SELECT * FROM person";
            ResultSet result = statement.executeQuery(SQL);
            while (result.next()) {
                Person person = new Person();
                person.setId(result.getInt("id"));
                person.setName(result.getString("name"));
                person.setAge(result.getInt("age"));
                person.setEmail(result.getString("email"));

                persons.add(person);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return persons;
    }

//    public Person show(int id) {
//        return people.stream().filter(person -> person.getId() == id).findFirst().orElse(null);
//    }

    public void save(Person person) {
//        person.setId(++PEOPLE_COUNT);
//        people.add(person);

        try {
            Statement statement = connection.createStatement();
            String SQL = "INSERT INTO person VALUES(" + 1 + ",'" + person.getName() +
                    "','" + person.getAge() + "','" + person.getEmail() + "')";
            statement.executeUpdate(SQL);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }
//    public void update(int id, Person updatedPerson) {
//        Person personToUpdate = show(id);
//        personToUpdate.setName(updatedPerson.getName());
//        personToUpdate.setEmail(updatedPerson.getEmail());
//        personToUpdate.setAge(updatedPerson.getAge());
//    }
//    public void delete(int id) {
//        people.removeIf(p -> p.getId() == id);
//    }

}
