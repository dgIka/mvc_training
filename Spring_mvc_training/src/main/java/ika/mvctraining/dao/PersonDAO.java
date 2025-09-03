package ika.mvctraining.dao;

import ika.mvctraining.models.Person;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class PersonDAO {
    private final SessionFactory sessionFactory;

    @Autowired
    public PersonDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Transactional(readOnly = true)
    public List<Person> index() {
        Session session = sessionFactory.getCurrentSession();
        Query<Person> query = session.createQuery("from Person", Person.class);
        List<Person> persons = query.list();
        if (persons.isEmpty()) {
            System.out.println("No persons found");
        } else {persons.stream().forEach(System.out::println);}
        System.out.println(debugDb());
        return persons;
    }

    @Transactional(readOnly = true)
    public String debugDb() {
        Object[] r = (Object[]) sessionFactory.getCurrentSession()
                .createNativeQuery("""
            select version(),
                   inet_server_addr(),
                   inet_server_port(),
                   current_database(),
                   current_user,
                   current_schema(),
                   (select count(*) from public.person)
        """)
                .getSingleResult();
        return String.format("version=%s host=%s port=%s db=%s user=%s schema=%s count(person)=%s",
                r[0], r[1], r[2], r[3], r[4], r[5], r[6]);
    }

    public Person show(int id) {
        return null;
    }

    public Person show(String email) {
        return null;
    }

    public void save(Person person) {
    }
    public void update(int id, Person updatedPerson) {
    }

    public void delete(int id) {
        }


}
