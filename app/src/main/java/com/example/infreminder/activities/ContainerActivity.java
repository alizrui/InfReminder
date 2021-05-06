package com.example.infreminder.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.infreminder.R;
import com.example.infreminder.activities.interfaces.I_ContainerActivity;
import com.example.infreminder.activitieslogic.ContainerActivityLogic;
import com.example.infreminder.activitieslogic.MainActivityLogic;
import com.example.infreminder.activitieslogic.interfaces.I_ContainerActivityLogic;
import com.example.infreminder.view.CreateAlarmView;
import com.example.infreminder.view.CreateReminderView;

public class ContainerActivity extends AppCompatActivity implements I_ContainerActivity {

    private I_ContainerActivityLogic logic;
    public static boolean aContainerOut = false;

    /**
     * Obtiene views de la interfaz y crea una instancia de la capa lógica.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container);
        int idButton = getIntent().getIntExtra("id",0);
        aContainerOut=false;

        logic = new ContainerActivityLogic(this);
        logic.updateFragments(idButton);
    }

    /**
     * Realiza el onBackPressed y finaliza la actividad para volver la anterior.
     */
    @Override
    public void onBackPressed() {
        aContainerOut=true;
        finish();
        super.onBackPressed();

    }

    /**
     * Devuelve el this de esta actividad.
     *
     * @return
     */
    @Override
    public ContainerActivity getContainerActivity() {
        return this;
    }
}