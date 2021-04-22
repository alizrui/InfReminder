package com.example.infreminder.database;

import android.app.ListActivity;

import com.example.infreminder.reminder.Reminder;
import com.example.infreminder.view.ReminderListView;

import java.lang.ref.WeakReference;
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

    private WeakReference<ReminderListView> weakReference;

    public DatabaseAccess(ReminderListView weakReference) {
        this.weakReference = new WeakReference<>(weakReference);
        selection = -1;
        update = false;
    }

    public void addReminder(Reminder reminder, boolean update) {
        this.reminder = reminder;
        this.update = update;
        selection = ADD_REMINDER;
        start();
    }

    public void deleteReminder(Reminder Reminder, boolean update) {
        this.reminder = reminder;
        this.update = update;
        selection = DELETE_REMINDER;
        start();
    }


    public void loadReminders() {
        selection = LOAD_ALL_REMINDERS;
        start();
    }

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

        if (update) {
            if (weakReference.get() == null) return;
            List<Reminder> reminders = ReminderDatabase.getInstance(weakReference.get().getContext()).reminderDao().getReminders();
            weakReference.get().getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    weakReference.get().updateList(reminders);
                }
            });
        }
    }
}
