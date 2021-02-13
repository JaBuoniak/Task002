package com.s4a;

import com.s4a.model.AirportCode;
import com.s4a.model.Flight;
import com.s4a.model.Weight;
import com.s4a.utils.TestDataUtil;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

public class LoadDistributionTest {

    @Test
    @SneakyThrows
    void shouldCountCargoWeightForRequestedFlight() {
        //given
        TestDataUtil testData = new TestDataUtil();
        Flight flight = TestDataUtil.generateFlight();
        flight.loadWithCargo(TestDataUtil.generateLoads(0,1));
        flight.loadWithBaggage(TestDataUtil.generateLoads(2,3));
        testData.flightsSchedule.addFlight(flight);

        //when
        Weight cargoWeight = testData.loadDistribution.howMuchCargoWeights(flight.number);

        //then
        assertThat(cargoWeight.kg()).isEqualTo(TestDataUtil.getWeightOfLoads(0,1));
    }

    @Test
    @SneakyThrows
    void shouldCountBaggageWeightForRequestedFlight() {
        //given
        TestDataUtil testData = new TestDataUtil();
        Flight flight = TestDataUtil.generateFlight();
        flight.loadWithCargo(TestDataUtil.generateLoads(0,1));
        flight.loadWithBaggage(TestDataUtil.generateLoads(2,3));
        testData.flightsSchedule.addFlight(flight);

        //when
        Weight baggageWeight = testData.loadDistribution.howMuchBaggageWeights(flight.number);

        //then
        assertThat(baggageWeight.kg()).isEqualTo(TestDataUtil.getWeightOfLoads(2,3));
    }

    @Test
    @SneakyThrows
    void shouldCountTotalLoadWeightForRequestedFlight() {
        //given
        TestDataUtil testData = new TestDataUtil();
        Flight flight = TestDataUtil.generateFlight();
        flight.loadWithCargo(TestDataUtil.generateLoads(0,1));
        flight.loadWithBaggage(TestDataUtil.generateLoads(2,3));
        testData.flightsSchedule.addFlight(flight);
        testData.flightsSchedule.addFlight(TestDataUtil.generateFlight());

        //when
        Weight loadWeight = testData.loadDistribution.howMuchTotalLoadWeights(flight.number);

        //then
        assertThat(loadWeight.kg()).isEqualTo(TestDataUtil.getWeightOfLoads(0,1,2,3));
    }

    @Test
    @SneakyThrows
    void shouldCountFlightsDepartedFromRequestedAirport() {
        //given
        TestDataUtil testData = new TestDataUtil();
        AirportCode departureAirportCode = AirportCode.GDN;
        Instant date = Instant.now();
        testData.flightsSchedule.addFlight(TestDataUtil.generateFlightFromTo(departureAirportCode, AirportCode.random(), date));

        //when
        testData.loadDistribution.howManyFlightsDepartedFrom(departureAirportCode, date);

        //then
        assertThat(false).isTrue();
    }

    @Test
    @SneakyThrows
    void shouldCountFlightsArrivedToRequestedAirport() {
        //given
        TestDataUtil testData = new TestDataUtil();
        AirportCode airportCode = AirportCode.GDN;
        Instant time = Instant.parse("2021-02-28T06:00:00Z");
        testData.flightsSchedule.addFlight(
                TestDataUtil.generateFlightFromTo(airportCode, AirportCode.random(), time));
        testData.flightsSchedule.addFlight(
                TestDataUtil.generateFlightFromTo(airportCode, AirportCode.random(), time.plus(3, ChronoUnit.HOURS)));
        testData.flightsSchedule.addFlight(
                TestDataUtil.generateFlightFromTo(airportCode, AirportCode.random(), time.plus(7, ChronoUnit.HOURS)));
        testData.flightsSchedule.addFlight(
                TestDataUtil.generateFlightFromTo(airportCode, AirportCode.random(), time.plus(11, ChronoUnit.HOURS)));


        //when
        testData.loadDistribution.howManyFlightsArrivedTo(airportCode, time);

        //then
        assertThat(false).isTrue();
    }

    @Test
    @SneakyThrows
    void shouldCountPiecesOfBaggageDepartedFromRequestedAirport() {
        //given
        TestDataUtil testData = new TestDataUtil();
        AirportCode airportCode = AirportCode.GDN;
        Instant time = Instant.parse("2021-02-28T06:00:00Z");
        testData.flightsSchedule.addFlight(
                TestDataUtil.generateFlightFromTo(airportCode, AirportCode.random(), time));
        testData.flightsSchedule.addFlight(
                TestDataUtil.generateFlightFromTo(airportCode, AirportCode.random(), time.plus(3, ChronoUnit.HOURS)));
        testData.flightsSchedule.addFlight(
                TestDataUtil.generateFlightFromTo(airportCode, AirportCode.random(), time.plus(7, ChronoUnit.HOURS)));
        testData.flightsSchedule.addFlight(
                TestDataUtil.generateFlightFromTo(airportCode, AirportCode.random(), time.plus(11, ChronoUnit.HOURS)));

        //when
        testData.loadDistribution.howManyPiecesOfBaggageDepartedFrom(airportCode, time);

        //then
        assertThat(false).isTrue();
    }

    @Test
    @SneakyThrows
    void shouldCountPiecesOfBaggageArrivedToRequestedAirport() {
        //given
        TestDataUtil testData = new TestDataUtil();
        AirportCode airportCode = AirportCode.GDN;
        Instant time = Instant.parse("2021-02-28T06:00:00Z");
        testData.flightsSchedule.addFlight(
                TestDataUtil.generateFlightFromTo(airportCode, AirportCode.random(), time));
        testData.flightsSchedule.addFlight(
                TestDataUtil.generateFlightFromTo(airportCode, AirportCode.random(), time.plus(3, ChronoUnit.HOURS)));
        testData.flightsSchedule.addFlight(
                TestDataUtil.generateFlightFromTo(airportCode, AirportCode.random(), time.plus(7, ChronoUnit.HOURS)));
        testData.flightsSchedule.addFlight(
                TestDataUtil.generateFlightFromTo(airportCode, AirportCode.random(), time.plus(11, ChronoUnit.HOURS)));

        //when
        testData.loadDistribution.howManyPiecesOfBaggageArrivedTo(airportCode, time);

        //then
        assertThat(false).isTrue();
    }
}
