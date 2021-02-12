package com.s4a;

import com.s4a.FlightsSchedule;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class FlightsScheduleTest {
  
  final static String FLIGHTS_JSON = "[\n" +
          "  {\n" +
          "    \"flightId\": 0,\n" +
          "    \"flightNumber\": 6487,\n" +
          "    \"departureAirportIATACode\": \"YYZ\",\n" +
          "    \"arrivalAirportIATACode\": \"PPX\",\n" +
          "    \"departureDate\": \"2015-05-27T02:44:59 -02:00\"\n" +
          "  },\n" +
          "  {\n" +
          "    \"flightId\": 1,\n" +
          "    \"flightNumber\": 1229,\n" +
          "    \"departureAirportIATACode\": \"YYT\",\n" +
          "    \"arrivalAirportIATACode\": \"PPX\",\n" +
          "    \"departureDate\": \"2016-03-26T08:24:50 -01:00\"\n" +
          "  }\n" +
          "]";
  
  @Test
  void shouldImportFlightsScheduleFromJson() {
    //given
    FlightsSchedule flightsSchedule = new FlightsSchedule();
    
    //when
    flightsSchedule.importFromJson(FLIGHTS_JSON);
    
    //then
    assertThat(flightsSchedule.findFlightById(0)).isPresent();
    assertThat(flightsSchedule.findFlightById(1)).isPresent();
  }
}