package com.s4a.model;

import org.assertj.core.data.Offset;
import org.junit.jupiter.api.Test;

import static com.s4a.model.WeightUnit.kg;
import static com.s4a.model.WeightUnit.lb;
import static org.assertj.core.api.Assertions.assertThat;

class WeightTest {

    @Test
    void shouldPresentWeightInKilograms() {
        //given
        Weight weight10lb = new Weight(10.0, lb);
        Weight weight200kg = new Weight(200.0, kg);

        //when
        double value1InKg = weight10lb.kg();
        double value2InKg = weight200kg.kg();

        //then
        assertThat(value1InKg).isCloseTo(4.535923, Offset.offset(0.001));
        assertThat(value2InKg).isEqualTo(200.0);

    }

    @Test
    void shouldPresentWeightInPounds() {
        //given
        Weight weight100lb = new Weight(100.0, lb);
        Weight weight20kg = new Weight(20.0, kg);

        //when
        double value1InLb = weight100lb.lb();
        double value2InLb = weight20kg.lb();

        //then
        assertThat(value1InLb).isEqualTo(100.0);
        assertThat(value2InLb).isCloseTo(44.09246, Offset.offset(0.001));
    }
}