package com.s4a.exceptions;

public class FlightAlreadyExistsException extends Exception {

    private final int id;

    public FlightAlreadyExistsException(int id) {
        this.id = id;
    }
    
    @Override
    public String getMessage() {
        return this.toString();
    }
    
    @Override
    public String toString() {
        return "Flight with ID " + id + " already exists in repository.";
    }
}
