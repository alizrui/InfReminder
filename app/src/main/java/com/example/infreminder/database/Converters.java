package com.example.infreminder.database;

import androidx.room.TypeConverter;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
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

    @TypeConverter
    public static ArrayList<String> fromString(String value) {
        Type listType = new TypeToken<ArrayList<String>>() {}.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromArrayList(ArrayList<String> list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }
}
