package my.playground.infrastructure.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class AppException extends RuntimeException {
  private final HttpStatus httpStatus;
  private final String message;

  private AppException(HttpStatus httpStatus, String message) {
    this.httpStatus = httpStatus;
    this.message = message;
  }

  public static AppException from(HttpStatus httpStatus, String message) {
    return new AppException(httpStatus, message);
  }


}

