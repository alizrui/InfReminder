package com.example.infreminder.logic;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import com.example.infreminder.database.DatabaseAccess;
import com.example.infreminder.database.ReminderDatabase;
import com.example.infreminder.logic.interfaces.I_CreateReminderLogic;
import com.example.infreminder.pojo.PojoInit;
import com.example.infreminder.pojo.Reminder;
import com.example.infreminder.threads.AlarmManagerThread;
import com.example.infreminder.view.interfaces.I_CreateReminderView;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CreateReminderLogic implements I_CreateReminderLogic {
    private I_CreateReminderView createReminderView;
    private DatabaseAccess dbAcces;

    public CreateReminderLogic(I_CreateReminderView fragment) {
        createReminderView = fragment;
        dbAcces = new DatabaseAccess(createReminderView.getCreateReminderView(),null,null);
    }

    @Override
    public void createReminder(String name, String features, ArrayList<String> days, Calendar calendar) {
        Reminder reminder = PojoInit.reminder(name,features,days,calendar);
        new Thread(() -> {
            List<Reminder> listRem = ReminderDatabase.getInstance(createReminderView.getCreateReminderView()
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
            dbAcces.addReminder(reminder, true);

            /* Crea AlarmManager para gestionar notificaciones*/
            AlarmManagerThread alarmManagerThread = new AlarmManagerThread(createReminderView.getCreateReminderView().getContext(),0);
            try {
                alarmManagerThread.throwAlarm(reminder);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }).start();

        createReminderView.getCreateReminderView().getActivity().onBackPressed();
    }


    @Override
    public void createAlertDialog(int title, int message, int botonYes, int botonNo) {
        AlertDialog.Builder builder = new AlertDialog.Builder(createReminderView.getCreateReminderView().getContext());
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton(botonYes, (dialog, which) -> {
            createReminderView.getCreateReminderView().getActivity().onBackPressed();
        });
        builder.setNegativeButton(botonNo, null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public boolean fieldEmpty(String s1, String s2) {
        return (s1.isEmpty() || s2.isEmpty()) ? false : true;
    }

    @Override
    public void showDatePickerDialog(TextInputEditText tie) {
        DatePickerFragment newFragment = DatePickerFragment.newInstance((datePicker, year, month, day) -> {
            String s1 = (day / 10 < 1) ? "0": "";
            String s2 = ((month + 1)  / 10 < 1) ? "0": "";
            // +1 because January is zero
            final String selectedDate = s1 + day + "/" + s2 + (month + 1) + "/" + year;
            tie.setText(selectedDate);
        });
        newFragment.show(createReminderView.getCreateReminderView().getActivity().getSupportFragmentManager(), "datePicker");
    }

    @Override
    public Calendar setDate(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date d = null;
        try {
            d = sdf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        cal.set(Calendar.HOUR,Calendar.getInstance().get(Calendar.HOUR_OF_DAY));
        cal.set(Calendar.MINUTE,Calendar.getInstance().get(Calendar.MINUTE));
        cal.set(Calendar.SECOND,Calendar.getInstance().get(Calendar.SECOND));
        return cal;
    }

    /**
     * Clase auxiliar para poder crear un DatePickerFragment
     */
    public static class DatePickerFragment extends DialogFragment {
        private DatePickerDialog.OnDateSetListener listener;
        public static DatePickerFragment newInstance(DatePickerDialog.OnDateSetListener listener) {
            DatePickerFragment fragment = new DatePickerFragment();
            fragment.setListener(listener);
            return fragment;
        }
        public void setListener(DatePickerDialog.OnDateSetListener listener) {
            this.listener = listener;
        }
        @Override
        @NonNull
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog d = new DatePickerDialog(getActivity(), listener, year, month, day);
            d.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
            return d;
        }
    }

}
