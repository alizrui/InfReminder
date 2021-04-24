package com.example.infreminder.logic.interfaces;

import java.util.ArrayList;

public interface I_CreateAlarmLogic {

    void createAlarm(int hour, int min, String name, ArrayList<String> features, ArrayList<Integer> days);

}
