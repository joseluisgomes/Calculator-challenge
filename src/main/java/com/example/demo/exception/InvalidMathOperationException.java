package com.example.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class InvalidMathOperationException extends RuntimeException {
    public InvalidMathOperationException(String message) {
        super(message);
    }

    public InvalidMathOperationException(String message, Throwable cause) {
        super(message, cause);
    }
}
