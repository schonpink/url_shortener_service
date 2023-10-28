package url_shortener.controller;

import url_shortener.dto.Error;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import url_shortener.exception.url.InvalidUrlException;
import url_shortener.exception.url.ShortUrlNotFoundException;

public class GlobalExceptionHandler {
    @ExceptionHandler(InvalidUrlException.class)
    public ResponseEntity<Error> handleInvalidUrlException(InvalidUrlException e) {
        Error error = new Error("Invalid Url", e.getMessage());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(error);
    }

    @ExceptionHandler(ShortUrlNotFoundException.class)
    public ResponseEntity<Error> handleShortUrlNotFoundException(ShortUrlNotFoundException e) {
        Error error = new Error("Short Url not found", e.getMessage());
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(error);
    }
}