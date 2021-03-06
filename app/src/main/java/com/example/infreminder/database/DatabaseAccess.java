package com.example.infreminder.database;

import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import com.example.infreminder.pojo.Reminder;
import com.example.infreminder.view.ReminderCalendarView;
import com.example.infreminder.view.ReminderListView;
import java.lang.ref.WeakReference;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class DatabaseAccess extends Thread {
    public final int ADD_REMINDER = 0;
    public final int DELETE_REMINDER = 1;
    public final int LOAD_ALL_REMINDERS = 2;
    public final int GET_REMINDER = 3;

    private int selection;
    private Reminder reminder;
    private int id;
    private boolean update;
    public static boolean isUpdated = false;

    private WeakReference<Fragment> weakReference;
    private WeakReference<ReminderListView> weakReference2;
    private WeakReference<ReminderCalendarView> weakReference3;

    /**
     * Clase utilizada para gestionar el acceso a la bd de forma estandarizada y tener tdo el control
     * desde un hilo
     *
     * @param weakReference (Cualquier fragment)
     * @param weakReference2 (ListViewFragment)
     * @param weakReference3 (CalendarViewFragment)
     */
    public DatabaseAccess(Fragment weakReference, ReminderListView weakReference2,ReminderCalendarView weakReference3) {
        this.weakReference = new WeakReference<>(weakReference);
        this.weakReference2 = (weakReference2 == null)? null : new WeakReference<>(weakReference2);
        this.weakReference3 = (weakReference3 == null)? null : new WeakReference<>(weakReference3);
        selection = -1;
        update = false;
    }

    /**
     * Añade un reminder a la BD y actualiza el adaptedList si procede
     *
     * @param reminder
     * @param update
     */
    public void addReminder(Reminder reminder, boolean update) {
        this.reminder = reminder;
        this.update = update;
        selection = ADD_REMINDER;
        start();
    }

    /**
     * Borra un reminder de la BD y actualiza el adapterList si procede
     * @param Reminder
     * @param update
     */
    public void deleteReminder(Reminder Reminder, boolean update) {
        this.reminder = reminder;
        this.update = update;
        selection = DELETE_REMINDER;
        start();
    }

    /**
     * Carga los reminder de la BD y actualiza el adapterList
     *
     */
    public void loadReminders() {
        selection = LOAD_ALL_REMINDERS;
        start();
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void run() {

        if (weakReference.get() == null) return;
        switch (selection) {
            case ADD_REMINDER :
                ReminderDatabase.getInstance(weakReference.get().getContext()).reminderDao().addReminder(reminder);
                break;
            case DELETE_REMINDER :
                ReminderDatabase.getInstance(weakReference.get().getContext()).reminderDao().deleteReminder(reminder);
                break;
            case LOAD_ALL_REMINDERS :
                update = true;
                break;
        }

        if (update && weakReference2 != null) {
            if (weakReference.get() == null) return;
            List<Reminder> reminders = ReminderDatabase.getInstance(weakReference.get().getContext()).reminderDao().getReminders();
            weakReference.get().getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    weakReference2.get().updateList(reminders);
                }
            });
        }

        if (update && weakReference3 != null) {
            if (weakReference.get() == null) return;
            long start = ReminderCalendarView.date.toEpochMilli();
            long end   = ReminderCalendarView.date.plus(1, ChronoUnit.DAYS).toEpochMilli();

            List<Reminder> aux = ReminderDatabase.getInstance(weakReference.get().getContext()).reminderDao().loadBetweenDate(start, end);
            List<Reminder> reminders = new ArrayList<>();
            for(int i= 0; i<aux.size();i++){
                if (aux.get(i).getDays().isEmpty()){
                    reminders.add(aux.get(i));
                }
            }
            weakReference.get().getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    weakReference3.get().updateList(reminders);
                }
            });
        }


    }
}
