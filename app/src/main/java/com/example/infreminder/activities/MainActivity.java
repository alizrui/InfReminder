package com.example.infreminder.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.View;

import com.example.infreminder.R;
import com.example.infreminder.activities.interfaces.I_MainActivity;
import com.example.infreminder.activitiespresenter.MainActivityPresenter;
import com.example.infreminder.activitiespresenter.interfaces.I_MainActivityPresenter;
import com.example.infreminder.adapter.ViewPagerFragmentStateAdapter;
import com.example.infreminder.fragmentsview.CreateAlarmFragment;
import com.example.infreminder.fragmentsview.ReminderListFragment;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class MainActivity extends AppCompatActivity implements I_MainActivity {

    private ViewPager2 pager2;

    private I_MainActivityPresenter logic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        logic = new MainActivityPresenter(this);

        pager2 = findViewById(R.id.viewPager2);

        pager2.setAdapter(new ViewPagerFragmentStateAdapter(this));

        TabLayout tabLayout = findViewById(R.id.tabLayout);

        new TabLayoutMediator(tabLayout, pager2, (tab, position) -> {
            switch (position) {
                case 0:
                    tab.setText("Lista");
                    break;
                case 1:
                    tab.setText("Calendario");
                    break;
            }
        }).attach();
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