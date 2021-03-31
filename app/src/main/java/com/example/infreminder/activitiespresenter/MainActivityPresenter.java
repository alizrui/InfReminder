package com.example.infreminder.activitiespresenter;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.infreminder.R;
import com.example.infreminder.activities.interfaces.I_MainActivity;
import com.example.infreminder.activitiespresenter.interfaces.I_MainActivityPresenter;
import com.example.infreminder.fragmentsview.CreateAlarmFragment;
import com.example.infreminder.fragmentsview.ReminderListFragment;

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
        int layout = 0;

        if (idButton == R.id.bCreate){
            // Keep a reference to the FragmentContainerView to be used to add the Fragment
            layout = R.id.fcvFragment;
            // Get a reference to the Fragment class
            fragmentToAdd = CreateAlarmFragment.class;

            bundle = new Bundle();
        } else if (idButton == R.id.bShow) {

            layout = R.id.fcvFragment;
            // Get a reference to the Fragment class
            fragmentToAdd = ReminderListFragment.class;

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
