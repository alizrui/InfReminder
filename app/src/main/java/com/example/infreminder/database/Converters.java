package com.example.infreminder.database;

import androidx.room.TypeConverter;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Converters {
    /**
     * Convierte un long a Calendar
     * Utilizado para guardar un calendario en formato timeInMillis dentro de la BD
     *
     * @param value
     * @return
     */
    @TypeConverter
    public static Calendar fromTimestamp(Long value) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(value);
        return calendar;
    }

    /**
     * Convierte un Calendar a long
     *Utilizado para extraer un long de la BD y convertirlo en calendarr
     *
     * @param calendar
     * @return
     */
    @TypeConverter
    public static Long dateToTimestamp(Calendar calendar) {
        return calendar == null ? null : calendar.getTimeInMillis();
    }

    /**
     * Convierte un string en un ArrayList<String>
     * Utilizado para extraer un String de días de la BD y que cada día se guarde en una posición
     *
     * @param value
     * @return
     */
    @TypeConverter
    public static ArrayList<String> fromString(String value) {
        Type listType = new TypeToken<ArrayList<String>>() {}.getType();
        return new Gson().fromJson(value, listType);
    }

    /**
     * Convierte un ArrayList<String> en un String
     * Utilizado para poder guardar un objeto tipo ArrayList<String> en la BD
     *
     * @param list
     * @return
     */
    @TypeConverter
    public static String fromArrayList(ArrayList<String> list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }
}
