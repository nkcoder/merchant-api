package my.playground.merchantapi.infrastructure.exception;

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

  @ExceptionHandler(value = {InvalidArgumentException.class})
  public ResponseEntity<ApiError> handleInvalidArgumentException(InvalidArgumentException ex) {
    logger.error("Invalid argument exception", ex);
    ApiError errorDetails = new ApiError(HttpStatus.BAD_REQUEST.value(), ex.getMessage(),
        System.currentTimeMillis());
    return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(value = {UserNotFoundException.class})
  public ResponseEntity<ApiError> handleUserNotFoundException(UserNotFoundException ex) {
    logger.error("User not found", ex);
    ApiError errorDetails = new ApiError(HttpStatus.BAD_REQUEST.value(), ex.getMessage(),
        System.currentTimeMillis());
    return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(value = {InvalidCredentialException.class})
  public ResponseEntity<ApiError> handleInvalidCredential(InvalidCredentialException ex) {
    logger.error("Invalid credential exception", ex);
    ApiError errorDetails = new ApiError(HttpStatus.FORBIDDEN.value(), ex.getMessage(),
        System.currentTimeMillis());
    return new ResponseEntity<>(errorDetails, HttpStatus.FORBIDDEN);
  }

  @ExceptionHandler(value = {MethodArgumentNotValidException.class})
  public ResponseEntity<ApiError> handleMethodArgumentNotValidException(
      MethodArgumentNotValidException ex) {
    logger.error("Invalid method argument exception", ex);
    String message = ex.getBindingResult().getFieldErrors().stream()
        .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
        .reduce("", (x, y) -> x + "| " + y);
    ApiError errorDetails = new ApiError(HttpStatus.BAD_REQUEST.value(), message,
        System.currentTimeMillis());
    return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ApiError> handleExceptions(Exception ex) {
    logger.error("Exception occurred", ex);
    ApiError errorDetails = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR.value(),
        ex.getMessage(), System.currentTimeMillis());
    return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
