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

        // Obteniene un FragmentTransaction para comenzar algunas operaciones con el FragmentManager actual
        FragmentTransaction transaction = view.getContainerActivity().getSupportFragmentManager()
                .beginTransaction().setCustomAnimations(
                        R.anim.enter_right_to_left,
                        R.anim.exit_right_to_left,
                        R.anim.enter_left_to_right,
                        R.anim.exit_left_to_right);
        transaction.setReorderingAllowed(true);

        // Reemplaza los fragmentos en el layout por el seleccionado
        if (fragmentToAdd != null) {
            transaction.add(layout, fragmentToAdd, bundle);
        }
        // Agrega la transacción al BackStack, para que pueda revertirse mientras se presiona el botón Atrás
        transaction.addToBackStack(null);
        // Hacemos los cambios efectivos
        transaction.commit();
    }

}
