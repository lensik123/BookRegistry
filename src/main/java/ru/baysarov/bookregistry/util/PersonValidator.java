package ru.baysarov.bookregistry.util;

import java.time.Year;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.baysarov.bookregistry.dao.PersonDao;
import ru.baysarov.bookregistry.model.Person;

@Component
public class PersonValidator implements Validator {

  private final PersonDao personDao;

  @Autowired
  public PersonValidator(PersonDao personDao) {
    this.personDao = personDao;
  }

  @Override
  public boolean supports(Class<?> aClass) {
    return Person.class.equals(aClass);
  }

  @Override
  public void validate(Object o, Errors errors) {
    Person person = (Person) o;

    if (personDao.show(person.getFullName()).isPresent()) {
      errors.rejectValue("fullName", "error.person", "A person with this name already exists");
    }

    if (person.getYearOfBirth() != null) {
      int currentYear = Year.now().getValue();
      int personAge = currentYear - person.getYearOfBirth();
      if (personAge < 18) {
        errors.rejectValue("yearOfBirth", "", "You have to be 18 or older to get a book");
      }
    }

  }
}
