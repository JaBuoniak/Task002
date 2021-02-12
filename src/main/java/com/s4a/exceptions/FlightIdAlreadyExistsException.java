package com.s4a.exceptions;

public class FlightIdAlreadyExistsException extends Throwable {

    private int index;

    public FlightIdAlreadyExistsException(int index) {
        this.index = index;
    }

    @Override
    public String toString() {
        return "Flight with ID " + index + " already exists in repository.";
    }
}
