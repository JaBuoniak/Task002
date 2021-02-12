package com.s4a.controller;

import com.s4a.model.Load;
import org.json.JSONArray;
import org.json.JSONObject;

public class LoadDistribution {
  private final FlightsSchedule schedule;
  
  public LoadDistribution(FlightsSchedule schedule) {
    this.schedule = schedule;
  }
  
  void importFromJson(String jsonContent) {
    JSONArray jsonArray = new JSONArray();
    for (int i = 0; i < jsonArray.length(); i++) {
      JSONObject jsonObject = jsonArray.getJSONObject(i);
      
      schedule.findFlightById(jsonObject.getInt("flightId"))
              .ifPresent(flight -> {
                flight.loadWithBaggage(Load.parse(jsonObject.getJSONArray("baggage")));
                flight.loadWithCargo(Load.parse(jsonObject.getJSONArray("cargo")));
              });
    }
  }
}
