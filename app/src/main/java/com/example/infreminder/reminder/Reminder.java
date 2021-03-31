package com.example.infreminder.reminder;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "reminder_table")
public class Reminder {
    @PrimaryKey(autoGenerate = true)
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

    public Reminder(String name, String features, String days)
    {
        this.name = name;
        this.features = features;
        this.days = days;
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
}