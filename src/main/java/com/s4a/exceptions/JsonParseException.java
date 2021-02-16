package com.s4a.exceptions;

public class JsonParseException extends Throwable {
    private String exceptionMessage;

    public JsonParseException(String exceptionMessage) {
        this.exceptionMessage = exceptionMessage;
    }

    @Override
    public String toString() {
        return exceptionMessage;
    }
}
