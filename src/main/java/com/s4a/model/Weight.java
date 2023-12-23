package com.s4a.model;

import static com.s4a.model.WeightUnit.kg;
import static com.s4a.model.WeightUnit.lb;

public class Weight {
    private final double value;
    private final WeightUnit unit;

    public Weight(double weight, WeightUnit unit) {
        this.value = weight;
        this.unit = unit;
    }

    public Weight(double weight, String weightUnit) {
        this(weight, WeightUnit.valueOf(weightUnit));
    }

    public double kg() {
        return value * (unit.multiplier / kg.multiplier);
    }

    public double lb() {
        return value * (unit.multiplier / lb.multiplier);
    }

    public Weight add(Weight another) {
        return new Weight(this.kg() + another.kg(), kg);
    }
    
    @Override
    public String toString() {
        return String.format("%.0f kg\t| %.0f lb", kg(), lb());
    }
}
