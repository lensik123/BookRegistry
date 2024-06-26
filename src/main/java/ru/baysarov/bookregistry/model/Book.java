package ru.baysarov.bookregistry.model;


import javax.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Book {

  private int id;

  @NotEmpty(message = "Title should not be empty")
  private String title;

  @NotEmpty(message = "Author should not be empty")
  private String author;


  private int year;

  @Override
  public String toString() {
    return "Book{" +
        "id=" + id +
        ", title='" + title + '\'' +
        ", author='" + author + '\'' +
        ", year=" + year +
        '}';
  }
}
