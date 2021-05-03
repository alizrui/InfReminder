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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container);
        int idButton = getIntent().getIntExtra("id",0);
        aContainerOut=false;

        logic = new ContainerActivityLogic(this);
        logic.updateFragments(idButton);
    }

    @Override
    public void onBackPressed() {
        aContainerOut=true;
        finish();
        super.onBackPressed();

    }

    @Override
    public ContainerActivity getContainerActivity() {
        return this;
    }
}