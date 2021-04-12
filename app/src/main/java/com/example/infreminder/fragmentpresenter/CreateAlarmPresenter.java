package com.example.infreminder.fragmentpresenter;

import com.example.infreminder.fragmentpresenter.interfaces.I_CreateAlarmPresenter;
import com.example.infreminder.fragmentsview.interfaces.I_CreateAlarmFragment;

public class CreateAlarmPresenter implements I_CreateAlarmPresenter {

    private I_CreateAlarmFragment i_createAlarmFragment;

    public CreateAlarmPresenter(I_CreateAlarmFragment fragment) {
        i_createAlarmFragment = fragment;
    }


}
