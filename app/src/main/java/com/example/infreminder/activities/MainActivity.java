package com.example.infreminder.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;

import com.example.infreminder.R;
import com.example.infreminder.activities.interfaces.I_MainActivity;
import com.example.infreminder.activitiespresenter.MainActivityPresenter;
import com.example.infreminder.activitiespresenter.interfaces.I_MainActivityPresenter;

public class MainActivity extends AppCompatActivity implements I_MainActivity {

    private I_MainActivityPresenter logic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        logic = new MainActivityPresenter(this);

        //comentario
    }

    public void updateFragments(View view) {

        final int clickedButton = view.getId();
        logic.updateFragments(clickedButton);
    }

    @Override
    public MainActivity getMainActivity() {
        return this;
    }
}