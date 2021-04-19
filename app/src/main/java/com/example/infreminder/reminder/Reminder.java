package com.example.infreminder.reminder;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.json.JSONObject;

import java.util.Calendar;
import java.util.Date;

@Entity(tableName = "reminder_table")
public class Reminder {
    @PrimaryKey
    private int id;

    @NonNull
    @ColumnInfo(name = "reminder_name")
    private String name;

    @NonNull
    @ColumnInfo(name = "reminder_features")
    private String features;

    @NonNull
    @ColumnInfo(name= "reminder_days")
    private String days;

    @NonNull
    @ColumnInfo(name= "reminder_date")
    private Calendar date;



    public Reminder( String name, String features, String days, Calendar date)
    {
        //this.id = id;
        this.name = name;
        this.features = features;
        this.days = days;
        this.date = date;

        Calendar calendarSchema = Calendar.getInstance();
        calendarSchema.set(2021, 0, 0, 0, 0, 0);

        long millis = date.getTimeInMillis() - calendarSchema.getTimeInMillis();
        // date de alarmas, día de esta semana que vaya a sonar

        millis = millis / 1000;
        millis = millis / 60;

        millis = millis * 10;

        // falta comprobar que no haya igual
        this.id = (int) millis;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFeatures() { return features; }

    public void setFeatures(String features) { this.features = features; }

    public String getDays() { return days; }

    public void setDays(String days) { this.days = days; }

    public Calendar getDate() { return date; }

    public void setDate(Calendar date) { this.date = date; }
}
