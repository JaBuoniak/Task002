package com.s4a.model;

import com.s4a.exceptions.JsonParseException;
import com.s4a.utils.DateUtils;
import org.json.JSONObject;

import java.text.ParseException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class Flight {
    final public int id;
    final public int number;
    final private AirportCode departureAirportIATACode;
    final private AirportCode arrivalAirportIATACode;
    final private Instant departureDate;
    final private List<Load> baggage;
    final private List<Load> cargo;

    public Flight(int flightId, int flightNumber, AirportCode departureAirportIATACode, AirportCode arrivalAirportIATACode, Instant departureDate) {
        this.id = flightId;
        this.number = flightNumber;
        this.departureAirportIATACode = departureAirportIATACode;
        this.arrivalAirportIATACode = arrivalAirportIATACode;
        this.departureDate = departureDate;
        this.baggage = new ArrayList<>();
        this.cargo = new ArrayList<>();
    }

    public static Flight parse(JSONObject jsonObject) throws JsonParseException {
        String departureDate = jsonObject.getString("departureDate");
        int flightId = jsonObject.getInt("flightId");
        try {
            return new Flight(
                    flightId,
                    jsonObject.getInt("flightNumber"),
                    AirportCode.of(jsonObject.getString("departureAirportIATACode")),
                    AirportCode.of(jsonObject.getString("arrivalAirportIATACode")),
                    DateUtils.parseDate(departureDate));
        } catch (ParseException e) {
            String exceptionMessage = "Parse failure during data import from JSON format.\n" +
                    "Could not recognize date format of [" + departureDate + "] for flight ID: " + flightId +
                    e.getMessage();
            System.out.println(exceptionMessage);
            throw new JsonParseException(exceptionMessage);
        }
    }

    public void loadWithBaggage(List<Load> baggage) {
        this.baggage.addAll(baggage);
    }
    public void loadWithCargo(List<Load> cargo) {
        this.cargo.addAll(cargo);
    }

    public Weight getTotalCargoWeight() {
        return new Weight(cargo.stream().mapToDouble(Load::totalKgWeight).sum(), WeightUnit.kg);
    }

    public Weight getTotalBaggageWeight() {
        return new Weight(baggage.stream().mapToDouble(Load::totalKgWeight).sum(), WeightUnit.kg);
    }

    public boolean departsFrom(AirportCode departureAirportCode) {
        return departureAirportIATACode.equals(departureAirportCode);
    }

    public boolean arrivesTo(AirportCode arrivalAirportCode) {
        return arrivalAirportIATACode.equals(arrivalAirportCode);
    }

    public Instant getDepartureDate() {
        return departureDate;
    }

    public int getTotalBaggagePieces() {
        return baggage.stream().mapToInt(Load::getQuantity).sum();
    }
}
