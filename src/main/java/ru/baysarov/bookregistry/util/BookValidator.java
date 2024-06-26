package ru.baysarov.bookregistry.util;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.baysarov.bookregistry.model.Book;

@Component
public class BookValidator implements Validator {

  @Override
  public boolean supports(Class<?> aClass) {
    return Book.class.equals(aClass);
  }

  @Override
  public void validate(Object o, Errors errors) {

  }
}
