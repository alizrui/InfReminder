package com.example.infreminder.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.widget.ViewPager2;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.infreminder.R;
import com.example.infreminder.activities.interfaces.I_MainActivity;
import com.example.infreminder.activitieslogic.MainActivityLogic;
import com.example.infreminder.activitieslogic.interfaces.I_MainActivityLogic;
import com.example.infreminder.adapter.ViewPagerFragmentStateAdapter;
import com.example.infreminder.fragmentsview.InfoFragment;
//import com.example.infreminder.fragmentsview.SettingsFragment;
import com.example.infreminder.fragmentsview.SettingsFragment;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity implements I_MainActivity {

    private ViewPager2 pager;
    private FragmentContainerView fcView;
    private TabLayout tabLayout;


    private I_MainActivityLogic logic;
    private FloatingActionButton fMain, fReminder, fAlarm, fSpecial;
    private TextView tAlarm,tSpecial,tReminder;
    private ImageView backgroundWhite;
    private boolean isOpen;
    private Animation animFabOpen, animFabClose,animFabRotateForward, animFabRotateBackward,enterLeftToRight;

    private boolean fragmentActive;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragmentActive = false;
        backgroundWhite = findViewById(R.id.img_background_white);
        //Floating Buttons
        fMain = findViewById(R.id.fab_principal);
        fAlarm = findViewById(R.id.fab_alarm);
        fReminder = findViewById(R.id.fab_reminder);
        fSpecial = findViewById(R.id.fab_special);
        //Texts between floating buttons
        tAlarm = findViewById(R.id.t_alarm);
        tSpecial = findViewById(R.id.t_special);
        tReminder = findViewById(R.id.t_reminder);

        //Animations
        enterLeftToRight = AnimationUtils.loadAnimation(MainActivity.this,R.anim.enter_left_to_right);
        animFabOpen = AnimationUtils.loadAnimation(MainActivity.this,R.anim.fab_open);
        animFabClose = AnimationUtils.loadAnimation(MainActivity.this,R.anim.fab_close);

        // {Nieves} no consigo que funcione sin que al pasar de fragment desaparezca, seguiré intentandolo
       // animFabRotateForward= AnimationUtils.loadAnimation(MainActivity.this,R.anim.fab_rotate_forward);
       // animFabRotateBackward = AnimationUtils.loadAnimation(MainActivity.this,R.anim.fab_rotate_backward);

        isOpen = true;
        showMenu(null);

        logic = new MainActivityLogic(this);

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
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_settings, menu);
        return true;
    }

    @Override
    public void onBackPressed() {

        overridePendingTransition(R.anim.enter_left_to_right,R.anim.exit_right_to_left);

        if(fragmentActive){

            pager.setVisibility(View.VISIBLE);
            tabLayout.setVisibility(View.VISIBLE);
            pager.setAnimation(enterLeftToRight);
            tabLayout.setAnimation(enterLeftToRight);
            fcView.setVisibility(View.INVISIBLE);
            enterLeftToRight.start();
            fMain.setVisibility(View.VISIBLE);
        }


        super.onBackPressed();
    }

    public void updateFragments(View view) {

        /*  */
        showMenu(null);
        fragmentActive = true;
        pager.setVisibility(View.INVISIBLE);
        tabLayout.setVisibility(View.INVISIBLE);
        fcView.setVisibility(View.VISIBLE);
        fMain.setVisibility(View.INVISIBLE);

        final int clickedButton = view.getId();
        logic.updateFragments(clickedButton);
    }





    public void showMenu(View v){
        if (isOpen) {
            backgroundWhite.setVisibility(View.INVISIBLE);
            fAlarm.setAnimation(animFabClose);
            fSpecial.setAnimation(animFabClose);
            fReminder.setAnimation(animFabClose);
            animFabClose.start();

            fAlarm.setClickable(false);
            fSpecial.setClickable(false);
            fReminder.setClickable(false);

            tAlarm.setVisibility(View.INVISIBLE);
            tSpecial.setVisibility(View.INVISIBLE);
            tReminder.setVisibility(View.INVISIBLE);

            isOpen = false;
        } else {
            backgroundWhite.setVisibility(View.VISIBLE);
            fAlarm.setAnimation(animFabOpen);
            fSpecial.setAnimation(animFabOpen);
            fReminder.setAnimation(animFabOpen);
            animFabOpen.start();


            fAlarm.setClickable(true);
            fSpecial.setClickable(true);
            fReminder.setClickable(true);

            tAlarm.setVisibility(View.VISIBLE);
            tSpecial.setVisibility(View.VISIBLE);
            tReminder.setVisibility(View.VISIBLE);

            isOpen = true;
        }
    }


    @Override
    public MainActivity getMainActivity() {
        return this;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        fragmentActive = true;
        pager.setVisibility(View.INVISIBLE);
        tabLayout.setVisibility(View.INVISIBLE);
        fcView.setVisibility(View.VISIBLE);
        fMain.setVisibility(View.INVISIBLE);

        fAlarm.setVisibility(View.INVISIBLE);
        fSpecial.setVisibility(View.INVISIBLE);
        fReminder.setVisibility(View.INVISIBLE);
        tAlarm.setVisibility(View.INVISIBLE);
        tSpecial.setVisibility(View.INVISIBLE);
        tReminder.setVisibility(View.INVISIBLE);

        //int id = item.getItemId();
        //Class<? extends Fragment> fragmentToAdd = null;
        //Fragment fragmentToRemove = null; // not used yet
        Fragment settingsFragment = new SettingsFragment();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.fcvFragment,settingsFragment);
        ft.commit();
        return super.onOptionsItemSelected(item);
    }
    }
