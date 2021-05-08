package com.example.infreminder.logic.interfaces;

import com.example.infreminder.logic.CreateReminderLogic;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public interface I_CreateSpecialLogic {

    /**
     * Genera un DatePickerDialog al pulsar un RadioButton.
     *
     * @param date
     */
    void showDatePickerDialog(Calendar date);

    /**
     * Crea un recordatorio especial con formato de recordatorio.
     *
     * @param name nombre del reminder
     * @param date fecha que sonará
     * @param features características
     */
    void createSpecialReminder(String name, Calendar date, JSONObject features);

    /**
     * Crea un recordatorio especial con formato de alarma.
     *
     * @param hour hora
     * @param min minuto
     * @param name nombre del especial
     * @param days días que sonará
     * @param features características
     */
    void createSpecialAlarm(int hour, int min, String name, ArrayList<String> days, JSONObject features);

}
