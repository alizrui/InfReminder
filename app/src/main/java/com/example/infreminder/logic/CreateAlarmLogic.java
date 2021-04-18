package com.example.infreminder.logic;

import com.example.infreminder.logic.interfaces.I_CreateAlarmLogic;
import com.example.infreminder.view.interfaces.I_CreateAlarmView;

public class CreateAlarmLogic implements I_CreateAlarmLogic {

    private I_CreateAlarmView i_createAlarmView;

    public CreateAlarmLogic(I_CreateAlarmView fragment) {
        i_createAlarmView = fragment;
    }



}
