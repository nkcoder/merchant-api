package my.playground.merchantapi.infrastructure.exception;


public record ApiError(int status, String message, long timestamp) {

}

