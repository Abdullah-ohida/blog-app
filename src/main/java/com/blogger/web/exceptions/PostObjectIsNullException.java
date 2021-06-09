package com.blogger.web.exceptions;

public class PostObjectIsNullException extends Throwable{
    public PostObjectIsNullException() {
        super();
    }

    public PostObjectIsNullException(String message) {
        super(message);
    }

    public PostObjectIsNullException(String message, Throwable cause) {
        super(message, cause);
    }

    public PostObjectIsNullException(Throwable cause) {
        super(cause);
    }

    protected PostObjectIsNullException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
