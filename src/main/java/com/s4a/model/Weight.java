package com.s4a.model;

import static com.s4a.model.WeightUnit.kg;

public class Weight {
    final private double value;
    final private WeightUnit unit;

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
        return value * (unit.multiplier / WeightUnit.lb.multiplier);
    }

    public Weight add(Weight another) {
        return new Weight(this.kg() + another.kg(), kg);
    }
}
