package com.s4a.utils;

import java.text.ParseException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneId;

public class DateUtils {
  
  public static Instant parseDate(String dateString) throws ParseException {
    return OffsetDateTime.parse(dateString.replaceAll(" ", "")).toInstant();
  }

  public static boolean isSameDay(Instant date1, Instant date2) {
    LocalDate localDate1 = date1.atZone(ZoneId.systemDefault()).toLocalDate();
    LocalDate localDate2 = date2.atZone(ZoneId.systemDefault()).toLocalDate();
    return localDate1.isEqual(localDate2);
  }
}
