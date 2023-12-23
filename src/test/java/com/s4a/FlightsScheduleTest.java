package com.s4a;

import com.s4a.exceptions.FlightAlreadyExistsException;
import com.s4a.exceptions.JsonParseException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class FlightsScheduleTest {
  
  final static String FLIGHTS_JSON =
          """
                  [{
                      "flightId": 0,
                      "flightNumber": 6487,
                      "departureAirportIATACode": "YYZ",
                      "arrivalAirportIATACode": "PPX",
                      "departureDate": "2015-05-27T02:44:59 -02:00"
                    },
                    {
                      "flightId": 1,
                      "flightNumber": 1229,
                      "departureAirportIATACode": "YYT",
                      "arrivalAirportIATACode": "PPX",
                      "departureDate": "2016-03-26T08:24:50 -01:00"
                  }]""";
  
  @Test
  void shouldImportFlightsScheduleFromJson() throws JsonParseException, FlightAlreadyExistsException {
    //given
    FlightsSchedule flightsSchedule = new FlightsSchedule();
    
    //when
    flightsSchedule.importFromJson(FLIGHTS_JSON);
    
    //then
    assertThat(flightsSchedule.findFlightById(0)).isPresent();
    assertThat(flightsSchedule.findFlightById(1)).isPresent();
  }
}