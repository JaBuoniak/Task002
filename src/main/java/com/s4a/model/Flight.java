package com.s4a.model;

import java.time.Instant;
import java.util.List;

public class Flight {
    private int flightId;
    private int flightNumber;
    private AirportCode departureAirportIATACode;
    private AirportCode arrivalAirportIATACode;
    private Instant departureDate;
    private List<Load> loads;
}
