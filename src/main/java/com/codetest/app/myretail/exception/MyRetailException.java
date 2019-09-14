package com.codetest.app.myretail.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Server Error Occurred")
@Data
public class MyRetailException extends Exception {

    private static final long serialVersionUID = 1L;
    private int errorCode;
    private String errorMessage;

    public MyRetailException() {
    }

    public MyRetailException(int errorCode, String errorMessage) {
        super(errorMessage);
        this.errorMessage = errorMessage;
        this.errorCode = errorCode;
    }
}
