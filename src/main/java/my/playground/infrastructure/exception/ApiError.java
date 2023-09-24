package my.playground.infrastructure.exception;


public record ApiError(int status, String message, long timestamp) {

}

