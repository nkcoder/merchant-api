package my.playground.onlineshop.infrastructure.exception;


public record ApiError(int status, String message, long timestamp) {

}

