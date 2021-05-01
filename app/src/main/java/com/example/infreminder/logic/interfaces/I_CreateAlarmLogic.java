package com.example.infreminder.logic.interfaces;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public interface I_CreateAlarmLogic {

    void createAlarm(int hour, int min, String name, JSONObject jsonObject, ArrayList<String> days) throws JSONException;

    void repeatOnlyOnce(int hour, int min, List<String> days);
    void repeatEveryDay(List<String> days);

}
