package com.s4a.model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Load {
    @SuppressWarnings("squid:S1068")
    private final int id;
    private final Weight weight;
    private final int quantity;
    
    public Load(int id, double weight, String weightUnit, int quantity) {
        this.id = id;
        this.weight = new Weight(weight, weightUnit);
        this.quantity = quantity;
    }
    
    public static List<Load> parse(JSONArray loadsArray) {
        ArrayList<Load> loadsList = new ArrayList<>();
        for (int i = 0; i < loadsArray.length(); i++) {
            JSONObject jsonObject = loadsArray.getJSONObject(i);
            loadsList.add(new Load(
                    jsonObject.getInt("id"),
                    jsonObject.getDouble("weight"),
                    jsonObject.getString("weightUnit"),
                    jsonObject.getInt("pieces")
            ));
        }
        return loadsList;
    }

    public double totalKgWeight() {
        // Przyjąłem, że ciężar jest ciężarem pojedyńczej sztuki ładunku, zatem mnożę go przez ilość.
        return weight.kg() * quantity;
    }

    public int getQuantity() {
        return quantity;
    }
}
