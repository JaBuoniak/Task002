package com.s4a.model;

import java.util.Locale;

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
}
