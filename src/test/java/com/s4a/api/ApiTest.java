package com.s4a.api;

import com.s4a.controller.FlightsSchedule;
import com.s4a.model.AirportCode;
import org.junit.jupiter.api.Test;

import java.util.Date;

public class ApiTest {
    Api api = new Api();
    FlightsSchedule schedule = new FlightsSchedule();

    @Test
    void shouldCountCargoWeightForRequestedFlight() {
        //given
        int flightNumber = 1234;
        //schedule.addFlight(flightNumber);
        //when
        api.howMuchCargoWeights(flightNumber);

        //then

    }

    @Test
    void shouldCountBaggageWeightForRequestedFlight() {
        //given
        int flightNumber = 1234;
        //schedule.addFlight(flightNumber);
        //when
        api.howMuchBaggageWeights(flightNumber);

        //then

    }

    @Test
    void shouldCountTotalLoadWeightForRequestedFlight() {
        //given
        int flightNumber = 1234;
        //schedule.addFlight(flightNumber);

        //when
        api.howMuchLoadWeights(flightNumber);

        //then

    }

    @Test
    void shouldCountFlightsDepartedFromRequestedAirport() {
        //given
        AirportCode airportCode = AirportCode.GDN;
        Date date = new Date();

        //when
        api.howManyFlightsDepartedFrom(airportCode, date);

        //then

    }

    @Test
    void shouldCountFlightsArrivedToRequestedAirport() {
        //given
        AirportCode airportCode = AirportCode.GDN;
        Date date = new Date();

        //when
        api.howManyFlightsArrivedTo(airportCode, date);

        //then

    }

    @Test
    void shouldCountPiecesOfBaggageDepartedFromRequestedAirport() {
        //given
        AirportCode airportCode = AirportCode.GDN;
        Date date = new Date();

        //when
        api.howManyPiecesOfBaggageDepartedFrom(airportCode, date);

        //then

    }

    @Test
    void shouldCountPiecesOfBaggageArrivedToRequestedAirport() {
        //given
        AirportCode airportCode = AirportCode.GDN;
        Date date = new Date();

        //when
        api.howManyPiecesOfBaggageArrivedTo(airportCode, date);

        //then

    }
}
