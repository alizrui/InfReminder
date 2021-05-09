package com.example.infreminder.logic;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.sip.SipSession;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.DialogFragment;

import com.example.infreminder.R;
import com.example.infreminder.Utils.Utils;
import com.example.infreminder.database.ReminderDatabase;
import com.example.infreminder.logic.interfaces.I_CreateSpecialLogic;
import com.example.infreminder.pojo.PojoInit;
import com.example.infreminder.pojo.Reminder;
import com.example.infreminder.pojo.Wiki;
import com.example.infreminder.threads.AlarmManagerThread;
import com.example.infreminder.view.CreateReminderView;
import com.example.infreminder.view.CreateSpecialView;
import com.example.infreminder.view.interfaces.I_CreateSpecialView;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;


public class CreateSpecialLogic implements I_CreateSpecialLogic {

    private I_CreateSpecialView view;

    private String wiki;

    public CreateSpecialLogic(I_CreateSpecialView view){
        this.view = view;
    }

    @Override
    public void showDatePickerDialog(Calendar calendar) {

        DatePickerFragment newFragment = DatePickerFragment.newInstance((datePicker, year, month, day) -> {
            calendar.set(Calendar.YEAR,year);
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.DAY_OF_MONTH, day);
        });

        newFragment.show(view.getCreateSpecialView().getActivity().getSupportFragmentManager(), "datePicker");
    }

    @Override
    public void createSpecialReminder(String name, Calendar date, JSONObject features) {
        date.set(Calendar.SECOND, 0);

        int vib = checkVibration();
        try {
            features.put("vibration_mode", vib);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Reminder reminder = PojoInit.reminder(name, Utils.jsonToString(features), new ArrayList<>(), date);
        new Thread(() -> {
            List<Reminder> listRem = ReminderDatabase.getInstance(view.getCreateSpecialView()
                    .getContext()).reminderDao().getReminders();

            /* Comprueba que no existen otros recordatorios con ese id */
            boolean everythingOK = false;
            while(!everythingOK){
                boolean exists = false;
                int myid = reminder.getId();
                for(Reminder rem:listRem){
                    if (rem.getId() == myid) {
                        exists = true;
                        break;
                    }
                }
                if(exists){
                    reminder.setId(myid + 1);
                } else {
                    everythingOK = true;
                }
            }
            /* Crea AlarmManager para gestionar notificaciones*/
            AlarmManagerThread alarmManagerThread = new AlarmManagerThread(view.getCreateSpecialView().getContext(),0);
            try {
                alarmManagerThread.throwAlarm(reminder);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            ReminderDatabase.getInstance(view.getCreateSpecialView().getContext()).reminderDao().addReminder(reminder);
        }).start();
    }

    @Override
    public void createSpecialAlarm(int hour, int min, String name, ArrayList<String> days, JSONObject features) {
        Calendar rightNow = Calendar.getInstance();
        int daysToNext = daysToNext(days, rightNow, hour, min);

        /* GregorianCalendar(year,month,dayofmonth,hourofday,minute) */
        Calendar dateAlarm = new GregorianCalendar(rightNow.get(Calendar.YEAR), rightNow.get(Calendar.MONTH), rightNow.get(Calendar.DAY_OF_MONTH), hour, min,0);
        dateAlarm.add(Calendar.DAY_OF_MONTH, daysToNext);

        int vib = checkVibration();
        try {
            features.put("vibration_mode", vib);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Reminder reminder = PojoInit.reminder(name, Utils.jsonToString(features), days, dateAlarm);

        new Thread(() -> {
            List<Reminder> listRem = ReminderDatabase.getInstance(view.getCreateSpecialView().getContext()).reminderDao().getReminders();

            /* Comprueba que no existen otros recordatorios con ese id */
            boolean everythingOK = false;
            while(!everythingOK){
                boolean exists = false;
                int myid = reminder.getId();
                for(Reminder rem:listRem){
                    if (rem.getId() == myid) {
                        exists = true;
                        break;
                    }
                }
                if(exists){ /* Por cada 10 sonará un minuto después */
                    reminder.setId(myid + 1);
                } else {
                    everythingOK = true;
                }
            }

            /* Crea AlarmManager para gestionar notificaciones*/
            AlarmManagerThread alarmManagerThread = new AlarmManagerThread(view.getCreateSpecialView().getContext(),0);
            try {
                alarmManagerThread.throwAlarm(reminder);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            ReminderDatabase.getInstance(view.getCreateSpecialView().getContext()).reminderDao().addReminder(reminder);
        }).start();
    }

    @Override
    public boolean isConnected() {
        boolean result = false;
        ConnectivityManager manager = (ConnectivityManager) view.getCreateSpecialView().getContext()
        .getSystemService(view.getCreateSpecialView().getContext().CONNECTIVITY_SERVICE);
        final Network activeNetwork = manager.getActiveNetwork();
        if (activeNetwork != null) {
            final NetworkCapabilities networkCapabilities = manager.getNetworkCapabilities(activeNetwork);
            result = networkCapabilities != null && (
                    networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                            networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI));
        }
        return result;
    }


    /**
     * Comprueba la vibración que hay seleccionada en preferences.
     */
    private int checkVibration(){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(view.getCreateSpecialView().getContext());
        int vib = 0;
        String vibPref = prefs.getString("vibration","");
        android.content.res.Resources resources = view.getCreateSpecialView().getContext().getResources();

        if(vibPref.equals(resources.getString(R.string.vibrate_short))){
            vib=1;
        } else if (vibPref.equals(resources.getString(R.string.vibrate_long))){
            vib=2;
        } else if (vibPref.equals(resources.getString(R.string.vibrate_special))){
            vib=3;
        }


        return vib;
    }

    /**
     * Comprueba cuantos días faltan para la siguiente alarma y los devuelve.
     *
     * @param days lista con días de la alarma
     * @param rightNow calendar del momento actual
     * @param hour hora de la alarma
     * @param min minuto de la alarma
     * @return días que faltan para la siguiente alarma
     */
    private int daysToNext(ArrayList<String> days, Calendar rightNow, int hour, int min){
        int today = rightNow.get(Calendar.DAY_OF_WEEK);
        int aux, daysToNext = 9;
        boolean jumpToday = false;

        /* Si la hora es anterior a la actual, faltarían 7 días en caso de que la alarma sonara hoy*/
        if (rightNow.get(Calendar.HOUR_OF_DAY) > hour ||
                (rightNow.get(Calendar.HOUR_OF_DAY) == hour && rightNow.get(Calendar.MINUTE) >= min)) {
            jumpToday = true;
        }
        for(String day:days){
            int int_day = Integer.parseInt(day);
            aux = (int_day - today < 0) ? int_day - today + 7: int_day - today;
            if(aux == 0 && jumpToday) aux = 7; // comprueba si hay que saltar el día de hoy
            if (aux < daysToNext) {
                daysToNext = aux; // daysToNext son los días que falta hasta que suene la alarma
            }
        }
        return daysToNext;
    }


    /**
     * DialogFragment encargado de que el usuario elija el día que corresponde
     */
    public static class DatePickerFragment extends DialogFragment {

        private DatePickerDialog.OnDateSetListener listener;

        public static DatePickerFragment newInstance(DatePickerDialog.OnDateSetListener listener){
            DatePickerFragment fragment = new DatePickerFragment();
            fragment.setListener(listener);
            return fragment;
        }

        public void setListener(DatePickerDialog.OnDateSetListener listener){
            this.listener = listener;
        }

        @NonNull
        @Override
        public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
            Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog d = new DatePickerDialog(getActivity(), listener, year, month, day);
            d.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
            return d;
        }
    }
}
