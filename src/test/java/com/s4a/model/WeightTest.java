package com.s4a.model;

import org.assertj.core.data.Offset;
import org.junit.jupiter.api.Test;

import static com.s4a.model.WeightUnit.*;
import static org.assertj.core.api.Assertions.assertThat;

class WeightTest {

    @Test
    void shouldPresentWeightInKilograms() {
        //given
        Weight weight = new Weight(100.0, kg);

        //when
        double valueInKg = weight.kg();

        //then
        assertThat(valueInKg).isEqualTo(100.0);

    }

    @Test
    void shouldPresentWeightInPounds() {
        //given
        Weight weight = new Weight(100.0, lb);

        //when
        double valueInKg = weight.kg();

        //then
        assertThat(valueInKg).isCloseTo(220.46, Offset.offset(0.01));

    }
}