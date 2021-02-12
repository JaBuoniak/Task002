package com.s4a.utils;

import com.s4a.model.AirportCode;
import com.s4a.model.Flight;

import java.time.Instant;
import java.util.concurrent.ThreadLocalRandom;

public class TestDataUtils {

    private static final int FLIGHT_ID_MAX = 9999;
    private static final int FLIGHT_NUMBER_MIN = 1000;
    private static final int FLIGHT_NUMBER_MAX = 9999;

    public static Flight generateFlight() {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        random.nextInt();
        return new Flight(
                random.nextInt(FLIGHT_ID_MAX),
                random.nextInt(FLIGHT_NUMBER_MIN, FLIGHT_NUMBER_MAX),
                AirportCode.random(),
                AirportCode.random(),
                Instant.now());
    }

}
