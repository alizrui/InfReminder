package com.example.infreminder.threads;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.fragment.app.FragmentActivity;

import com.example.infreminder.database.ReminderDao;
import com.example.infreminder.database.ReminderDatabase;
import com.example.infreminder.receivers.NotifyReceiver;
import com.example.infreminder.reminder.Reminder;

import java.util.Calendar;
import java.util.List;

public class AlarmManagerThread extends Thread {

    private final Context context;
    private final FragmentActivity activity;

    public AlarmManagerThread(FragmentActivity activity, Context context){
        this.activity = activity;
        this.context = context;
    }


    //public static void deleteAlarms(){
    //
    // }

    public void throwAlarm(Reminder rem){
        AlarmManager alarmManager = (AlarmManager) activity.getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(context, NotifyReceiver.class);
        intent.putExtra("id", rem.getId());
        intent.putExtra("title", rem.getName());

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, rem.getDate().getTimeInMillis(), pendingIntent);
    }

    @Override
    public void run() {
        /* Borra todos los reminders anteriores a la fecha actual */
        List<Reminder> reminders = ReminderDatabase.getInstance(context).reminderDao().getReminders();
        for (Reminder rem:reminders){
            if(rem.getDate().compareTo(Calendar.getInstance()) <= 0){
                /* Si es una alarma relanzar */


                /* Borrar si ya ha pasado */
                ReminderDatabase.getInstance(context).reminderDao().deleteReminder(rem);
            }
        }

    }
}
