package com.example.infreminder.logic.interfaces;

import com.example.infreminder.logic.CreateReminderLogic;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public interface I_CreateSpecialLogic {

    void showDatePickerDialog(Calendar date);

    void createSpecialReminder(String name, Calendar date, JSONObject features);
    void createSpecialAlarm(int hour, int min, String name, ArrayList<String> days, JSONObject features);

}
