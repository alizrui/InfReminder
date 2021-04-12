package com.example.infreminder.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentContainerView;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.OvershootInterpolator;
import android.widget.TextView;
import android.widget.Toast;

import com.example.infreminder.R;
import com.example.infreminder.activities.interfaces.I_MainActivity;
import com.example.infreminder.activitiespresenter.MainActivityPresenter;
import com.example.infreminder.activitiespresenter.interfaces.I_MainActivityPresenter;
import com.example.infreminder.adapter.ViewPagerFragmentStateAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity implements I_MainActivity {

    private ViewPager2 pager;
    private FragmentContainerView fcView;
    private TabLayout tabLayout;


    private I_MainActivityPresenter logic;
    private FloatingActionButton fMain, fReminder, fAlarm, fSpecial;
    private TextView tAlarm,tSpecial,tReminder;
    private boolean isOpen;
    //private Animation animFabOpen, animFabClose;

    private boolean fragmentActive;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentActive = false;

        //Floating Buttons
        fMain = findViewById(R.id.fab_principal);
        fAlarm = findViewById(R.id.fab_alarm);
        fReminder = findViewById(R.id.fab_reminder);
        fSpecial = findViewById(R.id.fab_special);
        //Texts between floating buttons
        tAlarm = findViewById(R.id.t_alarm);
        tSpecial = findViewById(R.id.t_special);
        tReminder = findViewById(R.id.t_reminder);
        //animFabOpen = AnimationUtils.loadAnimation(MainActivity.this,R.anim.fab_open);
        //animFabClose = AnimationUtils.loadAnimation(MainActivity.this,R.anim.fab_close);

        isOpen = true;
        showMenu(null);

        logic = new MainActivityPresenter(this);

        pager = findViewById(R.id.viewPager);
        fcView = findViewById(R.id.fcvFragment);
        tabLayout = findViewById(R.id.tabLayout);

        pager.setAdapter(new ViewPagerFragmentStateAdapter(this));

        TabLayout tabLayout = findViewById(R.id.tabLayout);

        new TabLayoutMediator(tabLayout, pager, (tab, position) -> {
            switch (position) {
                case 0:
                    tab.setText(getResources().getString(R.string.lista));
                    break;
                case 1:
                    tab.setText(getResources().getString(R.string.calendario));
                    break;
            }
        }).attach();

    }

    @Override
    public void onBackPressed() {
        if(fragmentActive){
            pager.setVisibility(View.VISIBLE);
            tabLayout.setVisibility(View.VISIBLE);
            fcView.setVisibility(View.INVISIBLE);
            fMain.setVisibility(View.VISIBLE);

        }

        super.onBackPressed();
    }

    public void updateFragments(View view) {

        /*  */
        fragmentActive = true;

        pager.setVisibility(View.INVISIBLE);
        tabLayout.setVisibility(View.INVISIBLE);
        fcView.setVisibility(View.VISIBLE);
        fMain.setVisibility(View.INVISIBLE);
        showMenu(null);


        final int clickedButton = view.getId();
        logic.updateFragments(clickedButton);
    }


    public void showMenu(View v){
        if (isOpen) {
            fAlarm.setVisibility(View.INVISIBLE);
            fSpecial.setVisibility(View.INVISIBLE);
            fReminder.setVisibility(View.INVISIBLE);
            tAlarm.setVisibility(View.INVISIBLE);
            tSpecial.setVisibility(View.INVISIBLE);
            tReminder.setVisibility(View.INVISIBLE);
            //Quiero ponerle animación, pero no funciona. Está en proceso (NIEVES)
            /*
                fAlarm.setAnimation(animFabClose);
                    fSpecial.setAnimation(animFabClose);
                    fReminder.setAnimation(animFabClose);
                    /*

                    */

            isOpen = false;
        } else {
            fAlarm.setVisibility(View.VISIBLE);
            fSpecial.setVisibility(View.VISIBLE);
            fReminder.setVisibility(View.VISIBLE);
            tAlarm.setVisibility(View.VISIBLE);
            tSpecial.setVisibility(View.VISIBLE);
            tReminder.setVisibility(View.VISIBLE);

            //Quiero ponerle animación, pero no funciona. Está en proceso (NIEVES)
            /*
            fAlarm.setAnimation(animFabOpen);
            fSpecial.setAnimation(animFabOpen);
            fReminder.setAnimation(animFabOpen);
            /*
            mAddUserText.setVisibility(View.VISIBLE);
            mAddContactText.setVisibility(View.VISIBLE);
            */
            isOpen = true;
        }
    }


    @Override
    public MainActivity getMainActivity() {
        return this;
    }
}