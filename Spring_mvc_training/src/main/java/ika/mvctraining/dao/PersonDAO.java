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

    public Person show(int id) {
        Person person = null;
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM person WHERE id = ?");
            statement.setInt(1, id);

            ResultSet result = statement.executeQuery();
            result.next();
            person = new Person();
            person.setId(result.getInt("id"));
            person.setName(result.getString("name"));
            person.setAge(result.getInt("age"));
            person.setEmail(result.getString("email"));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return person;
    }

    public void save(Person person) {


        try {
            PreparedStatement  preparedStatement = connection.prepareStatement("INSERT INTO person VALUES (1, ?, ?, ?)");
            preparedStatement.setString(1, person.getName());
            preparedStatement.setInt(2, person.getAge());
            preparedStatement.setString(3,  person.getEmail());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }
    public void update(int id, Person updatedPerson) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE person SET name=?, age=?, email=? WHERE id=?");
            preparedStatement.setString(1, updatedPerson.getName());
            preparedStatement.setInt(2, updatedPerson.getAge());
            preparedStatement.setString(3,  updatedPerson.getEmail());
            preparedStatement.setInt(4, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }}

    public void delete(int id) {
            try {
                PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM person WHERE id=?");
                preparedStatement.setInt(1, id);
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }


}
