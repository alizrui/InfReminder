package com.example.infreminder.reminder;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

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

    @ColumnInfo(name= "reminder_Date")
    private Date birthday;

    public Reminder(String name, String features, String days, Date birthday)
    {
        this.name = name;
        this.features = features;
        this.days = days;
        this.birthday = birthday;
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

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }
}
