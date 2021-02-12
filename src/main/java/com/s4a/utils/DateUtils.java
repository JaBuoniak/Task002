package com.s4a.utils;

import java.text.ParseException;
import java.time.Instant;
import java.time.OffsetDateTime;

public class DateUtils {
  
  public static Instant parseDate(String dateString) throws ParseException {
    return OffsetDateTime.parse(dateString.replaceAll(" ", "")).toInstant();
  }
}
