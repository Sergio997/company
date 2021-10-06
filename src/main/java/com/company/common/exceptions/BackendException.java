package com.company.common.exceptions;

import com.company.common.dtos.enums.ErrorCode;
import org.springframework.util.Assert;

public class BackendException extends RuntimeException {

    private static final long serialVersionUID = 2541463998888794608L;

    private final ErrorCode errorCode;

    public BackendException(ErrorCode errorCode) {
        super();
        Assert.notNull(errorCode, "Error code required");
        this.errorCode = errorCode;
    }

    public BackendException(ErrorCode errorCode, String message) {
        super(message);
        Assert.notNull(errorCode, "Error code required");
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
