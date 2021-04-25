package com.example.infreminder.logic.interfaces;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.Calendar;

public interface I_CreateReminderLogic {
    /**
     * Crea un recordatorio y lo inserta en la base de datos
     *
     * @param name Nombre del recordatorio
     * @param description Descripción del recordatorio (Opcional)
     * @param features Características del recordatorio
     * @param calendar Calendario del recordatorio
     */
    void createReminder(String name, String description, ArrayList<String> features, Calendar calendar);

    /**
     * Crea un dialogo con un alertDialog con los parámetros especificados
     *
     * @param title
     * @param message
     * @param botonYes
     * @param botonNo
     */
    void createAlertDialog(int title, int message, int botonYes, int botonNo);

    /**
     * Comprueba con una OR si dos String están vacíos
     * @param s1 String campo de texto 1
     * @param s2 String campo de texto 2
     * @return S1.isEmpty() || s2.isEmpty()
     */
    boolean fieldEmpty(String s1, String s2);

    /**
     * Genera un DatePickerDialog al pulsar un InputText
     * @param tie
     */
    void showDatePickerDialog(TextInputEditText tie);

    /**
     * Formatea un String con formato dd/mm/yyyy y crea un calendar con la hora del sistema
     * @param date
     * @return Un calendar con la fecha especificada
     */
    Calendar setDate(String date);
}

