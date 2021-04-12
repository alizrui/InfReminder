package com.example.infreminder.activitiespresenter;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.infreminder.R;
import com.example.infreminder.activities.interfaces.I_MainActivity;
import com.example.infreminder.activitiespresenter.interfaces.I_MainActivityPresenter;
import com.example.infreminder.fragmentsview.CreateAlarmFragment;
import com.example.infreminder.fragmentsview.CreateReminderFragment;
import com.example.infreminder.fragmentsview.ReminderListFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivityPresenter implements I_MainActivityPresenter {

    private I_MainActivity i_MainActivity;

    public MainActivityPresenter(I_MainActivity activity) {
        i_MainActivity = activity;
    }

    @Override
    public void updateFragments(int idButton) {
        Class<? extends Fragment> fragmentToAdd = null;
        Fragment fragmentToRemove = null; // not used yet

        Bundle bundle = null;
        int layout = R.id.fcvFragment;

        switch(idButton) {

            case R.id.bCreateAlarm:
                fragmentToAdd = CreateAlarmFragment.class;
                break;
            case R.id.bShow:
                fragmentToAdd = ReminderListFragment.class;
                break;
            case R.id.bCreateReminder:
                fragmentToAdd = CreateReminderFragment.class;
                break;
        }

        // Get a FragmentTransaction to begin some operations with the current FragmentManager
        FragmentTransaction transaction = i_MainActivity.getMainActivity().getSupportFragmentManager().beginTransaction();
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
