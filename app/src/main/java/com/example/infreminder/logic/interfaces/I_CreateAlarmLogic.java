package com.example.infreminder.logic.interfaces;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public interface I_CreateAlarmLogic {

    void createAlarm(int hour, int min, String name, JSONObject jsonObject, ArrayList<String> days) throws JSONException;

}
