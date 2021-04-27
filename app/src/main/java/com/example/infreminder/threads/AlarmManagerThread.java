package com.example.infreminder.threads;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.fragment.app.FragmentActivity;

import com.example.infreminder.Utils.Utils;
import com.example.infreminder.database.ReminderDatabase;
import com.example.infreminder.receivers.NotifyReceiver;
import com.example.infreminder.pojo.Reminder;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.List;

public class AlarmManagerThread extends Thread {

    private final Context context;
    private final int id;

    public AlarmManagerThread(Context context, int id){
        this.context = context;
        this.id = id;
    }


    //public static void deleteAlarms(){
    //
    // }

    public void throwAlarm(Reminder rem) throws JSONException {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(context, NotifyReceiver.class);
        intent.putExtra("id", rem.getId());
        intent.putExtra("name", rem.getName());

        /* Extraer campos del json repeatMinutes y replyText */
        JSONObject jsonObject = Utils.stringToJson(rem.getFeatures());
        Log.d("LOL", jsonObject.toString());
        intent.putExtra("replyText", (String) jsonObject.get("reply_text"));
        intent.putExtra("repeatEvery", (Integer) jsonObject.get("repeat_every"));

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, rem.getDate().getTimeInMillis(), pendingIntent);
    }

    @Override
    public void run() {
        /* Borra todos los reminders anteriores a la hora actual*/
        if(id == 0){
            /* Borra todos los reminders anteriores a la fecha actual */
            List<Reminder> reminders = ReminderDatabase.getInstance(context).reminderDao().getReminders();
            for (Reminder rem:reminders){
                if(rem.getDays().isEmpty()){
                    Log.d("LOL", "Estoy vacío y soy " + rem.getName());
                }
                Log.d("LOL", rem.getDate().compareTo(Calendar.getInstance())+"" );
                if(rem.getDate().compareTo(Calendar.getInstance()) <= 0) {
                    /* Si es una alarma con only_once = false relanzar */


                    /* Borrar si ya ha pasado */
                    ReminderDatabase.getInstance(context).reminderDao().deleteReminder(rem);
                }
            }
        } else { /* Borra el reminder por el que has sido lanzado */
            Reminder rem = ReminderDatabase.getInstance(context).reminderDao().getReminder(id);
            if (rem != null) {
                /* Elimina la alarma de la database */
                ReminderDatabase.getInstance(context).reminderDao().deleteReminder(rem);
            }
        }
    }
}
