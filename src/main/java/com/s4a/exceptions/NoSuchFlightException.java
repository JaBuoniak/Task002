package com.s4a.exceptions;

public class NoSuchFlightException extends Exception {
    private final int id;

    public NoSuchFlightException(int id) {
        this.id = id;
    }
    
    @Override
    public String getMessage() {
        return this.toString();
    }

    @Override
    public String toString() {
        return "Flight with ID " + id + " does not exists in repository.";
    }
}
