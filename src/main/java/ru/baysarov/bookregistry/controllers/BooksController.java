package ru.baysarov.bookregistry.controllers;

import java.util.Optional;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.baysarov.bookregistry.dao.BookDao;
import ru.baysarov.bookregistry.dao.PersonDao;
import ru.baysarov.bookregistry.model.Book;
import ru.baysarov.bookregistry.model.Person;
import ru.baysarov.bookregistry.util.BookValidator;

@Controller
@RequestMapping("/books")
public class BooksController {

  private final BookDao bookDao;
  private final BookValidator bookValidator;
  private final PersonDao personDao;

  @Autowired
  public BooksController(BookDao bookDao, BookValidator bookValidator, PersonDao personDao) {
    this.bookDao = bookDao;
    this.bookValidator = bookValidator;
    this.personDao = personDao;
  }

  @GetMapping()
  public String index(Model model) {
    model.addAttribute("books", bookDao.index());
    return "books/index";
  }

  @GetMapping("/{id}")
  public String show(@PathVariable("id") int id, Model model,
      @ModelAttribute("person") Person person) {
    model.addAttribute("book", bookDao.show(id));

    Optional<Person> bookOwner = bookDao.getOwner(id);
    if (bookOwner.isPresent()) {
      model.addAttribute("bookOwner",bookOwner);
    } else {
      model.addAttribute("people", personDao.index());
    }

    return "books/show";
  }

  @GetMapping("/new")
  public String addBook(@ModelAttribute("book") Book book) {
    return "books/new";
  }

  @PostMapping()
  public String create(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult) {
    bookValidator.validate(book, bindingResult);
    if (bindingResult.hasErrors()) {
      return "books/new";
    }

    bookDao.save(book);
    return "redirect:/books";
  }

  @GetMapping("/{id}/edit")
  public String edit(@PathVariable("id") int id, Model model) {
    model.addAttribute("book", bookDao.show(id));
    return "books/edit";
  }

  @PatchMapping("/{id}")
  public String update(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult,
      @PathVariable("id") int id) {
    bookValidator.validate(book, bindingResult);
    if (bindingResult.hasErrors()) {
      return "books/edit";
    }
    bookDao.update(id, book);
    return "redirect:/books";
  }

  @DeleteMapping("/{id}")
  public String delete(@PathVariable("id") int id) {
    bookDao.delete(id);
    return "redirect:/books";
  }

  @PatchMapping("/{id}/assign")
  public String assignBook(@PathVariable("id") int id, @ModelAttribute("person") Person person) {
    bookDao.assign(id, person.getId());
    return "redirect:/books";
  }

  @PatchMapping("/{id}/release")
  public String releaseBook(@PathVariable("id") int id){
    bookDao.release(id);
    return "redirect:/books";
  }


}
