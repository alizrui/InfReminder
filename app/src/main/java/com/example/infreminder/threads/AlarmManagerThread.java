package com.example.infreminder.threads;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.fragment.app.FragmentActivity;

import com.example.infreminder.Utils.Utils;
import com.example.infreminder.database.ReminderDatabase;
import com.example.infreminder.logic.CreateAlarmLogic;
import com.example.infreminder.pojo.PojoInit;
import com.example.infreminder.receivers.NotifyReceiver;
import com.example.infreminder.pojo.Reminder;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class AlarmManagerThread extends Thread {

    private final Context context;
    private final int id;

    public AlarmManagerThread(Context context, int id){
        this.context = context;
        this.id = id;
    }

    /**
     * Establece una notificación para el reminder recibido.
     *
     * @param rem reminder que sonará por la notificación
     * @throws JSONException
     */
    public void throwAlarm(Reminder rem) throws JSONException {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(context, NotifyReceiver.class);
        intent.putExtra("id", rem.getId());
        intent.putExtra("name", rem.getName());

        /* Extraer campos del json repeatMinutes y replyText */
        JSONObject jsonObject = Utils.stringToJson(rem.getFeatures());
        intent.putExtra("desc", (String) jsonObject.get("desc"));
        intent.putExtra("replyText", (String) jsonObject.get("reply_text"));
        intent.putExtra("repeatEvery", (Integer) jsonObject.get("repeat_every"));
        intent.putExtra("big_desc", (boolean) jsonObject.get("big_desc"));

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, rem.getDate().getTimeInMillis(), pendingIntent);
    }

    /**
     * Lanza una alarma que ya había sonado en la BD.
     *
     * @param hour
     * @param min
     * @param name
     * @param features
     * @param days
     * @throws JSONException
     */
    private void createAlarm(int hour, int min, String name, String features, ArrayList<String> days) throws JSONException {
        Calendar rightNow = Calendar.getInstance();
        int daysToNext = daysToNext(days, rightNow, hour, min);

        /* GregorianCalendar(year,month,dayofmonth,hourofday,minute) */
        Calendar dateAlarm = new GregorianCalendar(rightNow.get(Calendar.YEAR), rightNow.get(Calendar.MONTH), rightNow.get(Calendar.DAY_OF_MONTH), hour, min,0);
        dateAlarm.add(Calendar.DAY_OF_MONTH, daysToNext);

        Reminder reminder = PojoInit.reminder(name, features, days, dateAlarm);

        List<Reminder> listRem = ReminderDatabase.getInstance(context).reminderDao().getReminders();

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
        AlarmManagerThread alarmManagerThread = new AlarmManagerThread(context,0);
        try {
            alarmManagerThread.throwAlarm(reminder);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ReminderDatabase.getInstance(context).reminderDao().addReminder(reminder);
    }

    /**
     *  Comprueba cuantos días faltan para el siguiente día de alarma y los devuelve
     *  */
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
     * Gestiona el reminder que acaba de sonar.
     *
     * Un reminder que acaba de sonar debe ser borrado o no dependiendo de su atributo only_once
     *
     * @param rem
     */
    private void manageReminder(Reminder rem){
        if(rem.getDate().compareTo(Calendar.getInstance()) <= 0) {
            /* Si es una alarma con only_once = false relanzar */
            boolean only_once = true;

            try {
                JSONObject jsonObject = Utils.stringToJson(rem.getFeatures());
                only_once = jsonObject.getBoolean("only_once");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            ReminderDatabase.getInstance(context).reminderDao().deleteReminder(rem);
            if (!only_once) {
                try {
                    createAlarm(rem.getDate().get(Calendar.HOUR_OF_DAY), rem.getDate().get(Calendar.MINUTE),
                            rem.getName(), rem.getFeatures(), rem.getDays());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    @Override
    public void run() {
        /* Borra todos los reminders anteriores a la hora actual*/
        if (id == 0) {
            /* Borra todos los reminders anteriores a la fecha actual */
            List<Reminder> reminders = ReminderDatabase.getInstance(context).reminderDao().getReminders();
            for (Reminder rem : reminders) {
                manageReminder(rem);
            }

        } else { /* Borra el reminder por el que has sido lanzado */
            Reminder rem = ReminderDatabase.getInstance(context).reminderDao().getReminder(id);
            if(rem != null) manageReminder(rem);
        }
    }
}
