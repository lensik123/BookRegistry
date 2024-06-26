package ru.baysarov.bookregistry.dao;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.baysarov.bookregistry.model.Book;
import ru.baysarov.bookregistry.model.Person;

@Component
public class  PersonDao {

  private final JdbcTemplate jdbcTemplate;
  private final BeanPropertyRowMapper<Person> personRowMapper = new BeanPropertyRowMapper<>(
      Person.class);

  @Autowired
  public PersonDao(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  public List<Person> index() {
    return jdbcTemplate.query("SELECT * FROM Person", personRowMapper);
  }

  public Person show(int id) {
    return jdbcTemplate.query("SELECT * FROM Person WHERE id=?", new Object[]{id}, personRowMapper)
        .stream().findAny().orElse(null);
  }

  public Optional<Person> show(String fullName) {
    return jdbcTemplate.query("SELECT * from Person where full_name=?", new Object[]{fullName},
        new BeanPropertyRowMapper<>(Person.class)).stream().findAny();
  }

  public void save(Person person) {
    jdbcTemplate.update("INSERT INTO Person (full_name, year_of_birth) values (?, ?)",
        person.getFullName(), person.getYearOfBirth());
  }

  public void update(int id, Person person) {
    jdbcTemplate.update("UPDATE Person set full_name =?, year_of_birth=? where id=?",
        person.getFullName(), person.getYearOfBirth(), id);
  }

  public void delete(int id) {
    jdbcTemplate.update("DELETE FROM Person WHERE id=?", id);
  }

  public Optional<Person> isBookOwner(int id) {
    return jdbcTemplate.query("SELECT Person.* FROM Book JOIN Person ON Book.person_id = Person.id WHERE Book.person_id = ?", new Object[]{id}, new BeanPropertyRowMapper<>(Person.class)).stream().findAny();
  }
}


