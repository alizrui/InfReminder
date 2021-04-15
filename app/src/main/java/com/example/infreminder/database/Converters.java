package com.example.infreminder.database;

import androidx.room.TypeConverter;

import java.util.Calendar;
import java.util.Date;

public class Converters {

    @TypeConverter
    public static Calendar fromTimestamp(Long value) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(value);
        return calendar;
    }

    @TypeConverter
    public static Long dateToTimestamp(Calendar calendar) {
        return calendar == null ? null : calendar.getTimeInMillis();
    }
}
