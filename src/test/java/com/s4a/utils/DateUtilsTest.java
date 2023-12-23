package com.s4a.utils;


import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

class DateUtilsTest {
  
  @Test
  void shouldParseDate() {
    //given
    String dateString = "2017-01-26T09:14:23 -01:00";
    
    //when
    Instant parsedDate = DateUtils.parseDate(dateString);
    
    //then
    assertThat(parsedDate).isBeforeOrEqualTo("2017-01-26T10:15:00Z")
            .isAfterOrEqualTo("2017-01-26T10:14:00Z");
  }
}