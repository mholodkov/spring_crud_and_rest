package crud.book.exception;

import java.util.function.Supplier;

public class MyCoolException extends RuntimeException{

  public MyCoolException(String message) {
    super(message);
  }
}