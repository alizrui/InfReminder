package com.example.infreminder.logic.interfaces;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public interface I_CreateAlarmLogic {

    /**
     * Crea una alarma y la guarda en la base de datos.
     *
     * @param hour hora de la alarma
     * @param min minuto de la alarma
     * @param name nombre de la alarma
     * @param jsonObject características de la alarma
     * @param days días que sonara la alarma
     * @throws JSONException
     */
    void createAlarm(int hour, int min, String name, JSONObject jsonObject, ArrayList<String> days) throws JSONException;

    /**
     * Guarda en days el día que sonará la alarma (hoy o mañana)
     *
     * @param hour hora de la alarma
     * @param min minuto de la alarma
     * @param days día que sonará la alarma
     */
    void repeatOnlyOnce(int hour, int min, List<String> days);

    /**
     * Guarda en days todos los días de la semana.
     *
     * @param days
     */
    void repeatEveryDay(List<String> days);

}
