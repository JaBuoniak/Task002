package com.s4a;

import com.s4a.exceptions.FlightIdAlreadyExistsException;
import com.s4a.model.AirportCode;
import com.s4a.model.Flight;
import com.s4a.model.Weight;
import com.s4a.utils.TestDataUtil;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static java.time.temporal.ChronoUnit.HOURS;
import static org.assertj.core.api.Assertions.assertThat;

public class LoadDistributionTest {

    @Test
    void shouldCountCargoWeightForRequestedFlight() throws FlightIdAlreadyExistsException  {
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
    void shouldCountBaggageWeightForRequestedFlight() throws FlightIdAlreadyExistsException {
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
    void shouldCountTotalLoadWeightForRequestedFlight() throws FlightIdAlreadyExistsException {
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
    void shouldCountFlightsDepartedFromRequestedAirport() throws FlightIdAlreadyExistsException {
        //given
        TestDataUtil testData = new TestDataUtil();
        AirportCode departureAirport = AirportCode.GDN;
        Instant time = Instant.parse("2021-02-28T06:00:00Z");
        testData.flightsSchedule.addFlight(
                TestDataUtil.generateFlightFromTo(departureAirport, AirportCode.random(), time));
        testData.flightsSchedule.addFlight(
                TestDataUtil.generateFlightFromTo(departureAirport, AirportCode.random(), time.plus(3, HOURS)));
        testData.flightsSchedule.addFlight(
                TestDataUtil.generateFlightFromTo(departureAirport, AirportCode.random(), time.plus(7, HOURS)));
        testData.flightsSchedule.addFlight(
                TestDataUtil.generateFlightFromTo(departureAirport, AirportCode.random(), time.plus(11, HOURS)));

        //when
        int flightsDepartedFromGdn = testData.loadDistribution.howManyFlightsDepartedFrom(departureAirport, time);

        //then
        assertThat(flightsDepartedFromGdn).isGreaterThanOrEqualTo(4);
    }

    @Test
    void shouldCountFlightsArrivedToRequestedAirport() throws FlightIdAlreadyExistsException {
        //given
        TestDataUtil testData = new TestDataUtil();
        AirportCode arrivalAirport = AirportCode.GDN;
        Instant time = Instant.parse("2021-02-28T06:00:00Z");
        testData.flightsSchedule.addFlight(TestDataUtil.generateFlight());
        testData.flightsSchedule.addFlight(
                TestDataUtil.generateFlightFromTo(AirportCode.random(), arrivalAirport, time));
        testData.flightsSchedule.addFlight(
                TestDataUtil.generateFlightFromTo(AirportCode.random(), arrivalAirport, time.plus(3, HOURS)));
        testData.flightsSchedule.addFlight(
                TestDataUtil.generateFlightFromTo(AirportCode.random(), arrivalAirport, time.plus(7, HOURS)));
        testData.flightsSchedule.addFlight(
                TestDataUtil.generateFlightFromTo(AirportCode.random(), arrivalAirport, time.plus(11, HOURS)));

        //when
        int flightsArrivedTo = testData.loadDistribution.howManyFlightsArrivedTo(arrivalAirport, time);

        //then
        assertThat(flightsArrivedTo).isGreaterThanOrEqualTo(4);
    }

    @Test
    void shouldCountPiecesOfBaggageDepartedFromRequestedAirport() throws FlightIdAlreadyExistsException {
        //given
        TestDataUtil testData = new TestDataUtil();
        AirportCode departureAirport = AirportCode.GDN;
        Instant time = Instant.parse("2021-02-28T06:00:00Z");
        Flight flight1FromGdn = TestDataUtil.generateFlightFromTo(departureAirport, AirportCode.random(), time);
        Flight flight2FromGdn = TestDataUtil.generateFlightFromTo(departureAirport, AirportCode.random(), time.plus(4, HOURS));
        Flight randomFlight = TestDataUtil.generateFlight();
        flight1FromGdn.loadWithBaggage(TestDataUtil.generateLoads(1,2));
        flight2FromGdn.loadWithBaggage(TestDataUtil.generateLoads(2,3));
        randomFlight.loadWithBaggage(TestDataUtil.generateLoads(0));
        testData.flightsSchedule.addFlight(flight1FromGdn);
        testData.flightsSchedule.addFlight(flight2FromGdn);
        testData.flightsSchedule.addFlight(randomFlight);

        //when
        int piecesOfBaggageDepartedFrom = testData.loadDistribution.howManyPiecesOfBaggageDepartedFrom(departureAirport, time);

        //then
        assertThat(piecesOfBaggageDepartedFrom)
                .isGreaterThanOrEqualTo(TestDataUtil.QUANTITY[1] + TestDataUtil.QUANTITY[2]
                        + TestDataUtil.QUANTITY[2] + TestDataUtil.QUANTITY[3]);
    }

    @Test
    void shouldCountPiecesOfBaggageArrivedToRequestedAirport() throws FlightIdAlreadyExistsException {
        //given
        TestDataUtil testData = new TestDataUtil();
        AirportCode arrivalAirport = AirportCode.KRK;
        Instant time = Instant.parse("2021-02-28T06:00:00Z");
        Flight flight1ToKrk = TestDataUtil.generateFlightFromTo(AirportCode.random(), arrivalAirport, time);
        Flight flight2ToKrk = TestDataUtil.generateFlightFromTo(AirportCode.random(), arrivalAirport, time.plus(4, HOURS));
        Flight randomFlight = TestDataUtil.generateFlight();
        flight1ToKrk.loadWithBaggage(TestDataUtil.generateLoads(0,1,2));
        flight2ToKrk.loadWithBaggage(TestDataUtil.generateLoads(2,3));
        randomFlight.loadWithBaggage(TestDataUtil.generateLoads(0));
        testData.flightsSchedule.addFlight(flight1ToKrk);
        testData.flightsSchedule.addFlight(flight2ToKrk);
        testData.flightsSchedule.addFlight(randomFlight);

        //when
        int piecesOfBaggageArrivedTo = testData.loadDistribution.howManyPiecesOfBaggageArrivedTo(arrivalAirport, time);

        //then
        assertThat(piecesOfBaggageArrivedTo)
                .isGreaterThanOrEqualTo(TestDataUtil.QUANTITY[0] + TestDataUtil.QUANTITY[1] + TestDataUtil.QUANTITY[2]
                        + TestDataUtil.QUANTITY[2] + TestDataUtil.QUANTITY[3]);
    }
}
