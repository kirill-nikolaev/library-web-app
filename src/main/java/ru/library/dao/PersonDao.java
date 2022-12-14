package ru.library.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.library.models.Person;

import java.util.List;


@Component
public class PersonDao {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Person> findAll() {
        return jdbcTemplate.query("SELECT * FROM person", new PersonRowMapper());
    }

    public Person findById(int id) {
        return jdbcTemplate.query("SELECT * FROM person WHERE person_id=?",
                new Object[]{id}, new PersonRowMapper()).stream().findAny().orElse(null);
    }

    public void deleteById(int id) {
        jdbcTemplate.update("DELETE FROM person WHERE person_id = ?", id);
    }

    public void add(Person person) {
        jdbcTemplate.update("INSERT INTO person(name, year) VALUES (?, ?)",
                person.getName(), person.getYear());
    }

    public Person findByName(String name){
        System.out.println(name);
        Person person = jdbcTemplate.query("SELECT * FROM person WHERE name = ?",
                new Object[]{name}, new PersonRowMapper()).stream().findAny().orElse(null);
        if (person!= null)
            System.out.println(person.getId());
        return person;
    }

    public void updateById(int id, Person person) {
        jdbcTemplate.update("UPDATE person SET name = ?, year = ? WHERE person_id = ?",
                person.getName(), person.getYear(), id);
    }
}
