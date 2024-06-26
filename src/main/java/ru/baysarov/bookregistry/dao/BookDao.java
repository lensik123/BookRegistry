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
public class BookDao {

  private final JdbcTemplate jdbcTemplate;
  private final BeanPropertyRowMapper<Book> bookRowMapper = new BeanPropertyRowMapper<>(Book.class);



  @Autowired
  public BookDao(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  public List<Book> index() {
    return jdbcTemplate.query("Select * from book", bookRowMapper);
  }

  public List<Book> index(int id) {
    return jdbcTemplate.query("Select * from book where person_id=?",new Object[]{id}, bookRowMapper);
  }

  public Book show(int id) {
    return jdbcTemplate.query("SELECT * FROM Book where id=?", new Object[]{id}, bookRowMapper)
        .stream().findAny().orElse(null);
  }


  public void save(Book book) {
    jdbcTemplate.update("INSERT INTO Book (title, author, year) values (?,?,?)", book.getTitle(),
        book.getAuthor(), book.getYear());
  }

  public void update(int id, Book book) {
    jdbcTemplate.update("UPDATE Book SET title=?, author=?, year=? where id=?", book.getTitle(),
        book.getAuthor(), book.getYear(), id);
  }

  public void delete (int id){
    jdbcTemplate.update("DELETE FROM Book where id=?",id);
  }


  public void assign(int bookId, int personId) {
    jdbcTemplate.update("UPDATE Book set person_id=? where id=?", personId, bookId);
  }

  public void release(int bookId){
    jdbcTemplate.update("UPDATE Book set person_id=? where id=?",null,bookId);
  }

  public Optional<Person> getOwner(int id) {
    return jdbcTemplate.query("SELECT Person.* FROM Book JOIN Person ON Book.person_id = Person.id WHERE Book.id = ?", new Object[]{id}, new BeanPropertyRowMapper<>(Person.class))
        .stream().findAny();
  }
}
