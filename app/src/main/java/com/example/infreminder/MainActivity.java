package com.example.infreminder;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import database.ReminderDatabase;
import reminder.Reminder;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Reminder prueba = new Reminder("prueba","prueba",1);
        ReminderDatabase.getInstance(this).reminderDao().addReminder(prueba);
    }
}