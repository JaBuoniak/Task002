package com.s4a.model;

import java.util.Locale;
import java.util.Random;

public enum AirportCode {
    SEA,
    YYZ,
    YYT,
    ANC,
    LAX,
    MIT,
    LEW,
    GDN,
    KRK,
    PPX;

    private static final Random RANDOM = new Random();

    public static AirportCode of(String iataCodeString) {
        return valueOf(iataCodeString.trim().toUpperCase(Locale.getDefault()));
    }

    public static AirportCode random() {
        int possibilities = values().length;
        return values()[RANDOM.nextInt(possibilities)];

    }
}
