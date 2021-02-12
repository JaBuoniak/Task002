package com.s4a.model;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

public class Load {
    private int id;
    private Weight weight;
    private int quantity;
    
    public Load(int id, double weight, String weightUnit, int quantity) {
        this.id = id;
        this.weight = new Weight(weight, weightUnit);
        this.quantity = quantity;
    }
    
    public static List<Load> parse(JSONArray loadsArray) {
        ArrayList<Load> loadsList = new ArrayList<>();
        for (int i = 0; i < loadsArray.length(); i++) {
            loadsList.add(new Load(
                    loadsArray.getInt(i),
                    loadsArray.getDouble(i),
                    loadsArray.getString(i),
                    loadsArray.getInt(i)
            ));
        }
        return loadsList;
    }

    public double totalKgWeight() {
        return weight.kg() * quantity;
    }
}
