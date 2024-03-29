package com.s4a;

import com.s4a.exceptions.FlightAlreadyExistsException;
import com.s4a.exceptions.JsonParseException;
import com.s4a.exceptions.NoSuchFlightException;
import com.s4a.model.AirportCode;
import com.s4a.model.Flight;
import com.s4a.model.Load;
import com.s4a.model.Weight;
import org.json.JSONArray;
import org.json.JSONObject;

import java.time.Instant;
import java.util.Date;
import java.util.Optional;

import static com.s4a.model.WeightUnit.kg;

public class LoadDistribution {
    private final FlightsSchedule schedule;

    public LoadDistribution() {
        this.schedule = new FlightsSchedule();
    }

    public LoadDistribution(FlightsSchedule schedule) {
        this.schedule = schedule;
    }


    public Weight howMuchCargoWeights(int flightNumber, Instant time) {
        Optional<Flight> optionalFlight = schedule.findFlightsOfDate(time).stream()
                .filter(flight -> flight.number == flightNumber).findFirst();
        if (optionalFlight.isPresent()) {
            return optionalFlight.get().getTotalCargoWeight();
        } else
            return new Weight(0.0, kg);
    }

    public Weight howMuchBaggageWeights(int flightNumber, Instant time) {
        Optional<Flight> optionalFlight = schedule.findFlightsOfDate(time).stream()
                .filter(flight -> flight.number == flightNumber).findFirst();
        if (optionalFlight.isPresent()) {
            return optionalFlight.get().getTotalBaggageWeight();
        } else
            return new Weight(0.0, kg);
    }

    public Weight howMuchTotalLoadWeights(int flightNumber, Instant time) {
        Optional<Flight> optionalFlight = schedule.findFlightsOfDate(time).stream()
                .filter(flight -> flight.number == flightNumber).findFirst();
        if (optionalFlight.isPresent()) {
            return optionalFlight.get().getTotalBaggageWeight()
                    .add(optionalFlight.get().getTotalCargoWeight());
        } else
            return new Weight(0.0, kg);
    }

    public int howManyFlightsDepartedFrom(AirportCode departureAirportCode, Date date) {
        return howManyFlightsDepartedFrom(departureAirportCode, date.toInstant());
    }

    public int howManyFlightsDepartedFrom(AirportCode departureAirportCode, Instant date) {
        int counter = 0;
        for (Flight flight : schedule.findFlightsOfDate(date)) {
            if (flight.departsFrom(departureAirportCode))
                counter++;
        }
        return counter;
    }

    public int howManyFlightsArrivedTo(AirportCode arrivalAirportCode, Date date) {
        return howManyFlightsArrivedTo(arrivalAirportCode, date.toInstant());
    }

    public int howManyFlightsArrivedTo(AirportCode arrivalAirportCode, Instant date) {
        int counter = 0;
        for (Flight flight : schedule.findFlightsOfDate(date)) {
            if (flight.arrivesTo(arrivalAirportCode))
                counter++;
        }
        return counter;
    }

    public int howManyPiecesOfBaggageDepartedFrom(AirportCode departureAirportCode, Date date) {
        return howManyPiecesOfBaggageDepartedFrom(departureAirportCode, date.toInstant());
    }

    public int howManyPiecesOfBaggageDepartedFrom(AirportCode departureAirportCode, Instant date) {
        int counter = 0;
        for (Flight flight : schedule.findFlightsOfDate(date)) {
            if (flight.departsFrom(departureAirportCode))
                counter += flight.getTotalBaggagePieces();
        }
        return counter;
    }

    public int howManyPiecesOfBaggageArrivedTo(AirportCode arrivalAirportCode, Date date) {
        return howManyPiecesOfBaggageArrivedTo(arrivalAirportCode, date.toInstant());
    }

    public int howManyPiecesOfBaggageArrivedTo(AirportCode arrivalAirportCode, Instant date) {
        int counter = 0;
        for (Flight flight : schedule.findFlightsOfDate(date)) {
            if (flight.arrivesTo(arrivalAirportCode))
                counter += flight.getTotalBaggagePieces();
        }
        return counter;
    }


    public int importLoadsFromJson(String jsonContent) throws NoSuchFlightException {
        JSONArray jsonArray = new JSONArray(jsonContent);
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);

            int flightId = jsonObject.getInt("flightId");
            Optional<Flight> optionalFlight = schedule.findFlightById(flightId);
            if (optionalFlight.isPresent()) {
                optionalFlight.get().loadWithBaggage(Load.parse(jsonObject.getJSONArray("baggage")));
                optionalFlight.get().loadWithCargo(Load.parse(jsonObject.getJSONArray("cargo")));
            } else
                throw new NoSuchFlightException(flightId);
        }
        return jsonArray.length();
    }
    
    public int importFlightsFromJson(String jsonContent) throws JsonParseException, FlightAlreadyExistsException {
        return schedule.importFromJson(jsonContent);
    }
}
