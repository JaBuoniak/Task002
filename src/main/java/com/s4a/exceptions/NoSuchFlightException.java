package com.s4a.exceptions;

public class NoSuchFlightException extends Throwable {
    private int id;

    public NoSuchFlightException(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Flight with ID " + id + " does not exists in repository.";
    }
}
