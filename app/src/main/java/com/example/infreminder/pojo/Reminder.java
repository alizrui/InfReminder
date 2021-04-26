package com.example.infreminder.pojo;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;
import java.util.Calendar;

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
    @ColumnInfo(name = "reminder_days")
    private ArrayList<String> days;

    @NonNull
    @ColumnInfo(name= "reminder_date")
    private Calendar date;



    public Reminder(int id, String name, String features, ArrayList<String> days, Calendar date)
    {
        this.name = name;
        this.features = features;
        this.days = days;
        this.date = date;

//        Calendar calendarSchema = Calendar.getInstance();
//        calendarSchema.set(2021, 0, 0, 0, 0, 0);
//
//        long millis = date.getTimeInMillis() - calendarSchema.getTimeInMillis();
//
//        millis = millis / 1000;
//        millis = millis / 60;
//
//        millis = millis * 10;

        this.id = id;
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

    public ArrayList<String> getDays() { return days; }

    public void setDays(ArrayList<String> days) { this.days = days; }

    public Calendar getDate() { return date; }

    public void setDate(Calendar date) { this.date = date; }
}
