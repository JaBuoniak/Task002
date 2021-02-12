package com.s4a.model;

public enum WeightUnit {
    kg(1.0),
    lb(0.45359237);

    final double multiplier;

    WeightUnit(double multiplier) {
        this.multiplier = multiplier;
    }
}
