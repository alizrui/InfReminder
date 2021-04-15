package com.example.infreminder.reminder;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.json.JSONObject;

import java.util.Date;

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

    @ColumnInfo(name= "reminder_json")
    private String json;

    public Reminder(String name, String features, String days, String json)
    {
        this.name = name;
        this.features = features;
        this.days = days;
        this.json = json;
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

    public String getJson() { return json; }

    public void setJson(String json) { this.json = json; }
}
