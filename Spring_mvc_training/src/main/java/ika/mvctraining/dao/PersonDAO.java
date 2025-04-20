package ika.mvctraining.dao;

import ika.mvctraining.models.Person;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;

@Component
public class PersonDAO {
    private List<Person> people;

    {
        people = new ArrayList<Person>();

        people.add(new Person(1, "Tom"));
        people.add(new Person(2, "Bob"));
        people.add(new Person(3, "Mike"));
        people.add(new Person(4, "Shrek"));
    }

    public List<Person> index() {
        return people;
    }

    public Person shoew(int id) {
        return people.stream().filter(person -> person.getId() == id).findFirst().orElse(null);
    }
}
