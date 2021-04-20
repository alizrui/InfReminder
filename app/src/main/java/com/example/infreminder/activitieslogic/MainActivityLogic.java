package com.example.infreminder.activitieslogic;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.infreminder.R;
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
    public void updateFragments(int idButton) {

        Class<? extends Fragment> fragmentToAdd = null;
        Fragment fragmentToRemove = null; // not used yet

        Bundle bundle = null;
        int layout = R.id.fcvFragment;

        switch(idButton) {
            case R.id.fab_alarm:
                fragmentToAdd = CreateAlarmView.class;
                break;
            case R.id.fab_reminder:
                fragmentToAdd = CreateReminderView.class;
                break;
        }

        // Get a FragmentTransaction to begin some operations with the current FragmentManager
        FragmentTransaction transaction = i_MainActivity.getMainActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.enter_right_to_left,R.anim.exit_right_to_left,R.anim.enter_left_to_right,R.anim.exit_left_to_right);
        transaction.setReorderingAllowed(true);
        // Remove the required Fragment NOT USED YET
        if (fragmentToRemove != null) {
            transaction.remove(fragmentToRemove);
        }
        // Replace the Fragments in the required Layout by the selected one
        if (fragmentToAdd != null) {
            transaction.replace(layout, fragmentToAdd, bundle);
        }
        // Add the transaction to the BackStack, so it can be reversed by pressing the Back button
        transaction.addToBackStack(null);
        // Make changes effective
        transaction.commit();
    }



}
