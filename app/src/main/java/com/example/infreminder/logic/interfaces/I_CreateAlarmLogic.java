package com.example.infreminder.logic.interfaces;

import java.util.ArrayList;

public interface I_CreateAlarmLogic {

    void createAlarm(int id, int hour, int min, String name, String desc, ArrayList<Integer> days);

}
