package ru.baysarov.bookregistry.model;


import java.time.Year;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Person {

  private int id;

  @NotEmpty(message = "Name should not be Empty")
  @Pattern(regexp = "^[А-ЯЁ][а-яё]+ [А-ЯЁ][а-яё]+ [А-ЯЁ][а-яё]+$", message = "Формат ФИО должен быть такой: Иван Иванов Иванович ")
  private String fullName;

  @NotNull(message = "Year of birth should not be empty")
  @Min(value = 1900, message = "Year should be no less than 1900")
  @Max(value = Year.MAX_VALUE, message = "Year should be in the valid range")
  private Integer yearOfBirth;

  @Override
  public String toString() {
    return "Person{" +
        "id=" + id +
        ", fullName='" + fullName + '\'' +
        ", yearOfBirth=" + yearOfBirth +
        '}';
  }
}
