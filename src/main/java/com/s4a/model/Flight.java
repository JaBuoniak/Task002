package com.s4a.model;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class Flight {
    final private int flightId;
    final private int flightNumber;
    final private AirportCode departureAirportIATACode;
    final private AirportCode arrivalAirportIATACode;
    final private Instant departureDate;
    final private List<Load> loads;

    public Flight(int flightId, int flightNumber, AirportCode departureAirportIATACode, AirportCode arrivalAirportIATACode, Instant departureDate) {
        this.flightId = flightId;
        this.flightNumber = flightNumber;
        this.departureAirportIATACode = departureAirportIATACode;
        this.arrivalAirportIATACode = arrivalAirportIATACode;
        this.departureDate = departureDate;
        this.loads = new ArrayList<>();
    }

    void loadWith(Load load) {
        loads.add(load);
    }
}
