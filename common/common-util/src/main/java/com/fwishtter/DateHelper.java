package com.fwishtter;

import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

@Slf4j
public class DateHelper {

    public static String convertDatePatternToString(Date date, String pattern) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(date);
    }

    public static LocalDateTime localDateTimeIso8601() { return LocalDateTime.now(ZoneOffset.UTC); }

}
