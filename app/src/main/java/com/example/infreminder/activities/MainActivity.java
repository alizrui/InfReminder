package com.example.infreminder.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;

import com.example.infreminder.R;
import com.example.infreminder.fragmentsview.CreateAlarmFragment;
import com.example.infreminder.fragmentsview.ReminderListFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //comentario
    }

    public void updateFragments(View view) {
        Class<? extends Fragment> fragmentToAdd = null;
        Fragment fragmentToRemove = null; // not used yet

        Bundle bundle = null;
        int layout = 0;

        final int clickedButton = view.getId();


        if (clickedButton == R.id.bCreate){
            // Keep a reference to the FragmentContainerView to be used to add the Fragment
            layout = R.id.fcvFragment;
            // Get a reference to the Fragment class
            fragmentToAdd = CreateAlarmFragment.class;

            bundle = new Bundle();
        } else


        if (clickedButton == R.id.bShow) {

            layout = R.id.fcvFragment;
            // Get a reference to the Fragment class
            fragmentToAdd = ReminderListFragment.class;

        }
        // Get a FragmentTransaction to begin some operations with the current FragmentManager
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
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