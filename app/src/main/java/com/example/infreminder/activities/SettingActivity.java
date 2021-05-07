package com.example.infreminder.activities;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.example.infreminder.R;
import com.example.infreminder.fragmentsview.SettingsFragment;

public class SettingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        getSupportFragmentManager()
                .beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.fcvSettings, SettingsFragment.class, null)
                .commit();
    }


}
