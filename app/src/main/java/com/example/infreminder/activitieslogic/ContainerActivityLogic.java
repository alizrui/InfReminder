package com.example.infreminder.activitieslogic;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.infreminder.R;
import com.example.infreminder.activities.interfaces.I_ContainerActivity;
import com.example.infreminder.activities.interfaces.I_MainActivity;
import com.example.infreminder.activitieslogic.interfaces.I_ContainerActivityLogic;
import com.example.infreminder.view.CreateAlarmView;
import com.example.infreminder.view.CreateReminderView;
import com.example.infreminder.view.CreateSpecialView;

import java.io.Serializable;

public class ContainerActivityLogic implements I_ContainerActivityLogic {

    private I_ContainerActivity view;

    /**
     * Crear una instancia de la capa lógica del ContainerActivity.
     *
     * @param activity instancia del view
     */
    public ContainerActivityLogic(I_ContainerActivity activity) {
        view = activity;
    }

    /**
     * Lanza a ejecución el fragment seleccionado.
     *
     * @param idButton botón pulsado
     */
    @Override
    public void updateFragments(int idButton) {
        Class<? extends Fragment> fragmentToAdd = null;

        Bundle bundle = null;

        int layout = R.id.fcvContainer;

        switch(idButton) {
            case R.id.fab_alarm:
                fragmentToAdd = CreateAlarmView.class;
                break;
            case R.id.fab_reminder:
                fragmentToAdd = CreateReminderView.class;
                break;
            case R.id.fab_special:
                fragmentToAdd = CreateSpecialView.class;
                break;
        }

        // Get a FragmentTransaction to begin some operations with the current FragmentManager
        FragmentTransaction transaction = view.getContainerActivity().getSupportFragmentManager()
                .beginTransaction().setCustomAnimations(
                        R.anim.enter_right_to_left,
                        R.anim.exit_right_to_left,
                        R.anim.enter_left_to_right,
                        R.anim.exit_left_to_right);
        transaction.setReorderingAllowed(true);

        // Replace the Fragments in the required Layout by the selected one
        if (fragmentToAdd != null) {
            transaction.add(layout, fragmentToAdd, bundle);
        }
        // Add the transaction to the BackStack, so it can be reversed by pressing the Back button
        transaction.addToBackStack(null);
        // Make changes effective
        transaction.commit();
    }

}
