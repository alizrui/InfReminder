package com.example.infreminder.activitieslogic;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.infreminder.R;
import com.example.infreminder.activities.ContainerActivity;
import com.example.infreminder.activities.interfaces.I_MainActivity;
import com.example.infreminder.activitieslogic.interfaces.I_MainActivityLogic;
import com.example.infreminder.view.CreateAlarmView;
import com.example.infreminder.view.CreateReminderView;

public class MainActivityLogic implements I_MainActivityLogic {

    private I_MainActivity i_MainActivity;

    public MainActivityLogic(I_MainActivity activity) {
        i_MainActivity = activity;
    }

    @Override
    public void startActivityFragment(int idButton) {
        Intent intent_a = new Intent(i_MainActivity.getMainActivity(), ContainerActivity.class);

        intent_a.putExtra("id", idButton);
        i_MainActivity.getMainActivity().startActivity(intent_a);
    }
}
