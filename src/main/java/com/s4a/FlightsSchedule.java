package com.s4a;

import com.s4a.exceptions.FlightIdAlreadyExistsException;
import com.s4a.model.AirportCode;
import com.s4a.model.Flight;
import com.s4a.utils.DateUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.text.ParseException;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class FlightsSchedule {
    private final Map<Integer, Flight> flights;

    public FlightsSchedule() {
        flights = new HashMap<>();
    }

    void importFromJson(String jsonContent) {
        JSONArray jsonArray = new JSONArray(jsonContent);
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            int flightId = jsonObject.getInt("flightId");

            try {
                addFlight(new Flight(
                        flightId,
                        jsonObject.getInt("flightNumber"),
                        AirportCode.of(jsonObject.getString("departureAirportIATACode")),
                        AirportCode.of(jsonObject.getString("arrivalAirportIATACode")),
                        DateUtils.parseDate(jsonObject.getString("departureDate"))
                ));
            } catch (ParseException e) {
                System.out.println("Parse failure during data import from JSON format.\n" +
                        "Could not recognize date format of [" + jsonObject.getString("departureDate") + "] for flight ID: " + flightId);
            } catch (FlightIdAlreadyExistsException e) {
                System.out.println(e.toString());
            }
        }
    }

    public void addFlight(Flight flight) throws FlightIdAlreadyExistsException {
        if (flights.containsKey(flight.id)) {
            throw new FlightIdAlreadyExistsException(flight.id);
            //Lepsza oczywiście byłaby fabryka, która sama nadaje indeksy, ale skoro są podawane w danych wejściowych, to lepiej to zabezpieczyć.
        }
        flights.put(flight.id, flight);
    }

    public Optional<Flight> findFlightById(int flightId) {
        return Optional.ofNullable(flights.get(flightId));
    }

    public List<Flight> findFlightsOfDate(Instant date) {
        return flights.values().stream()
                .filter(flight ->
                        DateUtils.isSameDay(date, flight.getDepartureDate()))
                .collect(Collectors.toList());
    }
}
