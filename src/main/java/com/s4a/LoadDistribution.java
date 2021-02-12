package com.s4a;

import com.s4a.model.AirportCode;
import com.s4a.model.Flight;
import com.s4a.model.Load;
import com.s4a.model.Weight;
import org.json.JSONArray;
import org.json.JSONObject;

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


    public Weight howMuchCargoWeights(int flightNumber) {
        Optional<Flight> optionalFlight = schedule.findFlightByFlightNumber(flightNumber);
        if (optionalFlight.isPresent()) {
            return optionalFlight.get().getTotalCargoWeight();
        } else
            return new Weight(0.0, kg);
    }

    public Weight howMuchBaggageWeights(int flightNumber) {
        Optional<Flight> optionalFlight = schedule.findFlightByFlightNumber(flightNumber);
        if (optionalFlight.isPresent()) {
            return optionalFlight.get().getTotalBaggageWeight();
        } else
            return new Weight(0.0, kg);
    }

    public Weight howMuchTotalLoadWeights(int flightNumber) {
        Optional<Flight> optionalFlight = schedule.findFlightByFlightNumber(flightNumber);
        if (optionalFlight.isPresent()) {
            return optionalFlight.get().getTotalBaggageWeight()
                    .add(optionalFlight.get().getTotalCargoWeight());
        } else
            return new Weight(0.0, kg);
    }

    public int howManyFlightsDepartedFrom(AirportCode airportCode, Date date) {
        return 0;
    }

    public int howManyFlightsArrivedTo(AirportCode airportCode, Date date) {
        return 0;
    }

    public int howManyPiecesOfBaggageDepartedFrom(AirportCode airportCode, Date date) {
        return 0;
    }

    public int howManyPiecesOfBaggageArrivedTo(AirportCode airportCode, Date date) {
        return 0;
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
