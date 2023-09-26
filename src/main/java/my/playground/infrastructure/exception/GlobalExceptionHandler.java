package my.playground.infrastructure.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

  private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

  @ExceptionHandler(value = AppException.class)
  public ResponseEntity<ErrorDetails> handleAppException(AppException exception) {
    ErrorDetails errorDetails = new ErrorDetails(exception.getHttpStatus().value(), exception.getMessage(), System.currentTimeMillis());
    return new ResponseEntity<>(errorDetails, exception.getHttpStatus());
  }

  @ExceptionHandler(value = {MethodArgumentNotValidException.class})
  public ResponseEntity<ErrorDetails> handleMethodArgumentNotValidException(
      MethodArgumentNotValidException ex) {
    logger.error("Invalid method argument exception", ex);
    String message = ex.getBindingResult().getFieldErrors().stream()
        .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
        .reduce("", (x, y) -> x + "| " + y);
    ErrorDetails errorDetails = new ErrorDetails(HttpStatus.BAD_REQUEST.value(), message,
        System.currentTimeMillis());
    return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorDetails> handleExceptions(Exception ex) {
    logger.error("Exception occurred", ex);
    ErrorDetails errorDetails = new ErrorDetails(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage(),
        System.currentTimeMillis());
    return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
