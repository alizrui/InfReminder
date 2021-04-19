package com.example.infreminder.logic;

import android.util.Log;

import com.example.infreminder.logic.interfaces.I_CreateAlarmLogic;
import com.example.infreminder.view.interfaces.I_CreateAlarmView;

import java.util.ArrayList;
import java.util.Calendar;

public class CreateAlarmLogic implements I_CreateAlarmLogic {

    private I_CreateAlarmView i_createAlarmView;

    public CreateAlarmLogic(I_CreateAlarmView fragment) {
        i_createAlarmView = fragment;
    }

    @Override
    public void createAlarm(int id, int hour, int min, String name, String desc, ArrayList<Integer> days) {
        ArrayList<String> nameDays = new ArrayList<>();
        for(int i =days.)
        Log.d("LOL",  id + " " + hour + " " + min + " " + name + " " +desc + " " +days);
    }

    private String dayTranslator(int day){ // esto debería ir en la lógica? cual es la lógica?
        String res = "";
        switch(day){
            case Calendar.MONDAY: // 2
                res ="Monday";
                break;
            case Calendar.TUESDAY: // 3
                res = "Tuesday";
                break;
            case Calendar.WEDNESDAY: // 4
                res = "Wednesday";
                break;
            case Calendar.THURSDAY: // 5
                res = "Thursday";
                break;
            case Calendar.FRIDAY: // 6
                res = "Friday";
                break;
            case Calendar.SATURDAY: // 7
                res = "Saturday";
                break;
            case Calendar.SUNDAY: // 1
                res = "Sunday";
                break;
            default:
                res = "";
                break;
        }
        return res;
    }


}
