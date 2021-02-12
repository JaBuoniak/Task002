package com.s4a.controller;

import com.s4a.model.AirportCode;
import com.s4a.model.Flight;
import com.s4a.utils.DateUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Optional;

public class FlightsSchedule {
    private ArrayList<Flight> flights;
    
    public FlightsSchedule() {
        flights = new ArrayList<>();
    }
    
    void importFromJson(String jsonContent) {
        JSONArray jsonArray = new JSONArray(jsonContent);
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            int flightId = jsonObject.getInt("flightId");
            
            try {
                flights.add(flightId, new Flight(
                        flightId,
                        jsonObject.getInt("flightNumber"),
                        AirportCode.of(jsonObject.getString("departureAirportIATACode")),
                        AirportCode.of(jsonObject.getString("arrivalAirportIATACode")),
                        DateUtils.parseDate(jsonObject.getString("departureDate"))
                ));
            } catch (ParseException e) {
                System.out.println("Parse failure during data import from JSON format.\n" +
                        "Could not recognize date format of [" + jsonObject.getString("departureDate") + "] for flight ID: " + flightId);
            }
        }
    }
    
    public Optional<Flight> findFlightById(int flightId) {
        try {
            return Optional.ofNullable(flights.get(flightId));
        } catch (IndexOutOfBoundsException e) {
            return Optional.empty();
        }
    }
}
