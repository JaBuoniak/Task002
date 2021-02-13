package com.s4a.utils;

import com.s4a.FlightsSchedule;
import com.s4a.LoadDistribution;
import com.s4a.model.AirportCode;
import com.s4a.model.Flight;
import com.s4a.model.Load;
import lombok.Getter;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class TestDataUtil {

    public static final double[] WEIGHT = new double[]{150.1, 200.3, 34.5, 4721.0};
    public static final int[] QUANTITY = new int[]{4, 5, 1000, 2};

    private static final int FLIGHT_ID_MAX = 9999;
    private static final int FLIGHT_NUMBER_MIN = 1000;
    private static final int FLIGHT_NUMBER_MAX = 9999;

    public final FlightsSchedule flightsSchedule;
    public final LoadDistribution loadDistribution;

    public TestDataUtil() {
        flightsSchedule = new FlightsSchedule();
        loadDistribution = new LoadDistribution(flightsSchedule);
    }

    public static Flight generateFlight() {
        return generateFlightFromTo(AirportCode.random(), AirportCode.random(), Instant.now());
    }

    public static List<Load> generateLoads(int... variants) {
        List<Load> loads = new ArrayList<>();
        for (int i = 0; i < variants.length; i++) {
            int variant = variants[i];
            if (variant < WEIGHT.length)
                loads.add(new Load(i, WEIGHT[variant], "kg", QUANTITY[variant]));
        }
        return loads;
    }

    public static double getWeightOfLoads(int... variants) {
        double sum = 0;
        for (int i = 0; i < variants.length; i++) {
            if (i >= WEIGHT.length)
                sum += WEIGHT[i] * QUANTITY[i];
        }
        return sum;
    }

    public static Flight generateFlightFromTo(AirportCode departureAirport, AirportCode arrivalAirport, Instant date) {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        random.nextInt();
        return new Flight(
                random.nextInt(FLIGHT_ID_MAX),
                random.nextInt(FLIGHT_NUMBER_MIN, FLIGHT_NUMBER_MAX),
                departureAirport,
                arrivalAirport,
                date);
    }
}
