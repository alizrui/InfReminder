package com.example.infreminder.logic.interfaces;

import androidx.fragment.app.Fragment;

import com.example.infreminder.pojo.Reminder;
import com.example.infreminder.view.ReminderListView;

import java.util.List;

public interface I_ReminderListLogic {
    /**
     * Actualiza la lista con los nuevos recordatorios insertados
     */
    void updateReminders();
}
