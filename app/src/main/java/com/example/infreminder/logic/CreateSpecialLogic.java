package com.example.infreminder.logic;

import com.example.infreminder.logic.interfaces.I_CreateSpecialLogic;
import com.example.infreminder.view.interfaces.I_CreateSpecialView;

public class CreateSpecialLogic implements I_CreateSpecialLogic {

    private I_CreateSpecialView view;

    public CreateSpecialLogic(I_CreateSpecialView view){
        this.view = view;
    }

}
