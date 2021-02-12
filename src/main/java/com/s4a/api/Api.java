package com.s4a.api;

import com.s4a.model.AirportCode;
import com.s4a.model.Weight;

import java.util.Date;

import static com.s4a.model.WeightUnit.*;

public class Api {

    public Weight howMuchCargoWeights(int flightNumber) {
        return new Weight(123.9, lb);
    }

    public Weight howMuchBaggageWeights(int flightNumber) {
        return new Weight(123.9, lb);
    }

    public Weight howMuchLoadWeights(int flightNumber) {
        return new Weight(123.9, lb);
    }

    public int howManyFlightsDepartedFrom(AirportCode airportCode, Date date) {
        return 0;
    }

    public int howManyFlightsArrivedTo(AirportCode airportCode, Date date) {
        return 0;
    }

    public int howManyPiecesOfBaggageDepartedFrom(AirportCode airportCode, Date date) {
        return 0;
    }

    public int howManyPiecesOfBaggageArrivedTo(AirportCode airportCode, Date date) {
        return 0;
    }

}
