package com.example.infreminder.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentTransaction;
import androidx.preference.PreferenceManager;
import androidx.viewpager2.widget.ViewPager2;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
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
//import com.example.infreminder.fragmentsview.InfoFragment;
//import com.example.infreminder.fragmentsview.SettingsFragment;
import com.example.infreminder.fragmentsview.SettingsFragment;
import com.example.infreminder.view.CreateAlarmView;
import com.example.infreminder.view.CreateReminderView;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity implements I_MainActivity {

    private ViewPager2 pager;
    private TabLayout tabLayout;
    private I_MainActivityLogic logic;
    private FloatingActionButton fMain, fReminder, fAlarm, fSpecial;
    private TextView tAlarm,tSpecial,tReminder;
    private boolean isOpen;
    private Animation animFabOpen, animFabClose,enterLeftToRight;


    /**
     * onCreate de MainActivity.
     * Obtiene referencias de los views de la interfaz activity_main.xml e inicializa el tablayout del viewpager.
     * Crea una instancia de la capa l??gica MainActivityLogic.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

        isOpen = true;
        showMenu(null);

        logic = new MainActivityLogic(this);

        pager = findViewById(R.id.viewPager);
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
    /**
     * Crea el menu.
     *
     * @param menu
     * @return true porque siempre se crea el menu
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_settings, menu);
        return true;
    }

    /**
     * Realiza onBackPressed y a??ade una animaci??n.
     */
    @Override
    public void onBackPressed() {
        overridePendingTransition(R.anim.enter_left_to_right,R.anim.exit_right_to_left);
        super.onBackPressed();
    }

    /**
     * Llama a la capa l??gica para mostrar el fragment seleccionado.
     * @param
     */
    public void updateFragments(View view) {
        final int clickedButton = view.getId();
        logic.startActivityFragment(clickedButton);
    }

    /**
     * Modifica la visibilidad del menu de los floatingButton.
     * @param v
     */
    public void showMenu(View v){
        if (isOpen) {
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

    /**
     * Devuelve la actividad de esta pantalla.
     *
     * @return la actividad this
     */
    @Override
    public MainActivity getMainActivity() {
        return this;
    }

    /**
     * Lanza el fragment del settings.
     *
     * @param item
     * @return devuelve si la acci??n se ha completado correctamente.
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_item_settings ){
            if(isOpen){
                showMenu(null);
            }
            Intent intent = new Intent(this, SettingActivity.class);
            startActivity(intent);

        }else if(id == R.id.menu_item_info ){
            if(isOpen){
                showMenu(null);
            }
            showAbout();}

        return super.onOptionsItemSelected(item);
    }

    /**
     * Crea un di??logo con informaci??n de los creadores de la aplicaci??n.
     */
    private void showAbout() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.more_info)
                .setMessage(R.string.about_message)
                .setPositiveButton(android.R.string.ok, null)
                .show();
    }
}


