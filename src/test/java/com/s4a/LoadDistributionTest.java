package com.s4a;

import com.s4a.exceptions.FlightIdAlreadyExistsException;
import com.s4a.model.AirportCode;
import com.s4a.model.Flight;
import com.s4a.model.Load;
import com.s4a.model.Weight;
import com.s4a.utils.TestDataUtils;
import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

public class LoadDistributionTest {

    public static final double[] WEIGHT = new double[]{150.1, 200.3, 34.5, 4721.0};
    public static final int[] QUANTITY = new int[]{4, 5, 1000, 2};

    FlightsSchedule schedule;
    LoadDistribution distribution;

    @BeforeEach
    public void init() {
        schedule = new FlightsSchedule();
        distribution = new LoadDistribution(schedule);
    }

    private Flight prepareTestFlight() {
        Flight flight = TestDataUtils.generateFlight();
        try {
            flight.loadWithBaggage(Arrays.asList(
                    new Load(0, WEIGHT[0], "kg", QUANTITY[0]),
                    new Load(1, WEIGHT[1], "kg", QUANTITY[1])));
            flight.loadWithCargo(Arrays.asList(
                    new Load(0, WEIGHT[2], "kg", QUANTITY[2]),
                    new Load(1, WEIGHT[3], "kg", QUANTITY[3])));

            schedule.addFlight(flight);
        } catch (FlightIdAlreadyExistsException e) {
            System.out.println(e.toString());
        }
        return flight;
    }

    @Test
    void shouldCountCargoWeightForRequestedFlight() {
        //given
        Flight flight = prepareTestFlight();

        //when
        Weight cargoWeight = distribution.howMuchCargoWeights(flight.number);

        //then
        assertThat(cargoWeight.kg()).isEqualTo(WEIGHT[0] * QUANTITY[0] + WEIGHT[1] * QUANTITY[1]);
    }

    @Test
    void shouldCountBaggageWeightForRequestedFlight() {
        //given
        Flight flight = prepareTestFlight();

        //when
        Weight baggageWeight = distribution.howMuchBaggageWeights(flight.number);

        //then
        assertThat(baggageWeight.kg()).isEqualTo(WEIGHT[0] * QUANTITY[0] + WEIGHT[1] * QUANTITY[1]);
    }

    @Test
    void shouldCountTotalLoadWeightForRequestedFlight() {
        //given
        Flight flight = prepareTestFlight();

        //when
        Weight loadWeight = distribution.howMuchTotalLoadWeights(flight.number);

        //then
        assertThat(loadWeight.kg()).isEqualTo(WEIGHT[0] * QUANTITY[0] + WEIGHT[1] * QUANTITY[1]
                + WEIGHT[2] * QUANTITY[2] + WEIGHT[3] * QUANTITY[3]);
    }

    @Test
    void shouldCountFlightsDepartedFromRequestedAirport() {
        //given
        AirportCode airportCode = AirportCode.GDN;
        Date date = new Date();

        //when
        distribution.howManyFlightsDepartedFrom(airportCode, date);

        //then

    }

    @Test
    void shouldCountFlightsArrivedToRequestedAirport() {
        //given
        AirportCode airportCode = AirportCode.GDN;
        Date date = new Date();

        //when
        distribution.howManyFlightsArrivedTo(airportCode, date);

        //then

    }

    @Test
    void shouldCountPiecesOfBaggageDepartedFromRequestedAirport() {
        //given
        AirportCode airportCode = AirportCode.GDN;
        Date date = new Date();

        //when
        distribution.howManyPiecesOfBaggageDepartedFrom(airportCode, date);

        //then

    }

    @Test
    void shouldCountPiecesOfBaggageArrivedToRequestedAirport() {
        //given
        AirportCode airportCode = AirportCode.GDN;
        Date date = new Date();

        //when
        distribution.howManyPiecesOfBaggageArrivedTo(airportCode, date);

        //then

    }
}
