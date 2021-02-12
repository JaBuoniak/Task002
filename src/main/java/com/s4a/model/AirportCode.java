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
    
    public static AirportCode of(String IATACodeString) {
        return valueOf(IATACodeString.trim().toUpperCase(Locale.getDefault()));
    }

    public static AirportCode random() {
        int possibilities = values().length;
        return values()[new Random().nextInt(possibilities)];
    }
}
