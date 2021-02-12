package com.s4a.model;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Weight {
    final private double value;
    final private WeightUnit unit;

    public double kg() {
        return value * unit.multiplier;
    }

    public double lb() {
        return value * (unit.multiplier / WeightUnit.lb.multiplier);
    }
}
