package com.s4a;

import com.s4a.exceptions.FlightAlreadyExistsException;
import com.s4a.exceptions.JsonParseException;
import com.s4a.model.Flight;
import com.s4a.utils.DateUtils;
import org.json.JSONArray;

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

    int importFromJson(String jsonContent) throws JsonParseException, FlightAlreadyExistsException {
        JSONArray jsonArray = new JSONArray(jsonContent);
        for (int i = 0; i < jsonArray.length(); i++) {
            addFlight(Flight.parse(jsonArray.getJSONObject(i)));
        }
        return flights.size();
    }

    public void addFlight(Flight flight) throws FlightAlreadyExistsException {
        if (flights.containsKey(flight.id)) {
            throw new FlightAlreadyExistsException(flight.id);
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
