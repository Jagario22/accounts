package com.nix.module.module3.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Date;

public class DateUtil {
    private final static DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

    public static Timestamp getFormattedDate(String date) {
        Date formattedDate = null;
        try {
            formattedDate = formatter.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Timestamp timestamp = Timestamp.from(formattedDate.toInstant());
        return timestamp;
    }

    public static Instant getFormattedDate(Instant instant) {
        Date formattedDate = null;
        String date = formatDateToString(instant);

        try {
            formattedDate = formatter.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return formattedDate.toInstant();
    }

    public static String formatDateToString(Instant instant) {
        String formattedDate;
        Date date = Date.from(instant);
        formattedDate = formatter.format(date);
        return formattedDate;
    }
}
