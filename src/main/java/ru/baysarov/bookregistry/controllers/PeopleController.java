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
import ru.baysarov.bookregistry.util.PersonValidator;

@Controller
@RequestMapping("/people")
public class PeopleController {

  private final BookDao bookDao;
  private final PersonDao personDao;
  private final PersonValidator personValidatorvalidator;

  @Autowired
  public PeopleController(BookDao bookDao, PersonDao personDao, PersonValidator validator) {
    this.bookDao = bookDao;
    this.personDao = personDao;
    this.personValidatorvalidator = validator;
  }

  @GetMapping()
  public String index(Model model) {
    model.addAttribute("people", personDao.index());
    return "people/index";
  }

  @GetMapping("/{id}")
  public String show(@PathVariable("id") int id, Model model, @ModelAttribute("book") Book book) {
    model.addAttribute("person", personDao.show(id));

    Optional<Person> bookOwner = personDao.isBookOwner(id);

    if (bookOwner.isPresent()) {
      model.addAttribute("books", bookDao.index(id));
    } else {
      model.addAttribute("noBooks", "");
    }
    return "people/show";
  }

  @GetMapping("/new")
  public String newPerson(@ModelAttribute("person") Person person) {
    return "people/new";
  }

  @PostMapping
  public String create(@ModelAttribute("person") @Valid Person person,
      BindingResult bindingResult) {
    personValidatorvalidator.validate(person, bindingResult);
    if (bindingResult.hasErrors()) {
      return "people/new";
    }
    personDao.save(person);
    return "redirect:/people";
  }

  @GetMapping("/{id}/edit")
  public String edit(@PathVariable("id") int id, Model model) {
    model.addAttribute("person", personDao.show(id));
    return "people/edit";
  }

  @PatchMapping("/{id}")
  public String update(@ModelAttribute("person") Person person, BindingResult bindingResult,
      @PathVariable("id") int id) {
    personValidatorvalidator.validate(person, bindingResult);
    if (bindingResult.hasErrors()) {
      return "people/edit";
    }
    personDao.update(id, person);
    return "redirect:/people";
  }

  @DeleteMapping("/{id}")
  public String delete(@PathVariable("id") int id) {
    personDao.delete(id);
    return "redirect:/people";
  }

}
