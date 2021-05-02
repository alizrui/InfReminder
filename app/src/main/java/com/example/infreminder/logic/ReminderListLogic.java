package com.example.infreminder.logic;

import com.example.infreminder.database.DatabaseAccess;
import com.example.infreminder.logic.interfaces.I_ReminderListLogic;
import com.example.infreminder.pojo.Reminder;
import com.example.infreminder.view.ReminderListView;
import com.example.infreminder.view.interfaces.I_ReminderListView;

import java.util.List;

public class ReminderListLogic implements I_ReminderListLogic {
    private I_ReminderListView createReminderListView;
    private DatabaseAccess dbAccess;

    public ReminderListLogic(I_ReminderListView fragment) {
        createReminderListView = fragment;
        dbAccess = new DatabaseAccess(createReminderListView.getReminderListView(),
                createReminderListView.getReminderListView(), null);
    }

    @Override
    public void updateReminders() {
        dbAccess.loadReminders();
    }
}
