package com.gooten.core;

public class GTNException extends Exception {

    public GTNException() {
        super();
    }

    public GTNException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public GTNException(String detailMessage) {
        super(detailMessage);
    }

    public GTNException(Throwable throwable) {
        super(throwable);
    }

}
