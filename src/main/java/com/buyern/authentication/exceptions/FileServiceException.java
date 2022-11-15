package com.buyern.authentication.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_GATEWAY)
public class FileServiceException extends RuntimeException {
    public FileServiceException(String message) {
        super(message);
    }
}
