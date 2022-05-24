package com.derayah.gateway.exception;

import org.springframework.http.HttpStatus;

/**
 * CustomException.
 *
 * @author : Mo Sayed
 * @since : 5/24/2022
 */
public class CustomException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    private final String message;
    private final HttpStatus httpStatus;

    public CustomException(String message, HttpStatus httpStatus) {
        this.message = message;
        this.httpStatus = httpStatus;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
