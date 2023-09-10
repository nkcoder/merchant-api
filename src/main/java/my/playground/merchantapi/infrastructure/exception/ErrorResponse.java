package my.playground.merchantapi.infrastructure.exception;


public record ErrorResponse(int status, String message, long timestamp) {

}

