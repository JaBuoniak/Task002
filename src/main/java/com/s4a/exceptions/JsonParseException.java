package com.s4a.exceptions;

public class JsonParseException extends Throwable {
    private final String exceptionMessage;

    public JsonParseException(String exceptionMessage) {
        this.exceptionMessage = exceptionMessage;
    }
    
    @Override
    public String getMessage() {
        return exceptionMessage;
    }
    
    @Override
    public String toString() {
        return exceptionMessage;
    }
}
