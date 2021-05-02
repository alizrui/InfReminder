package com.example.infreminder.view;

import android.animation.Animator;
import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.Toast;


import com.example.infreminder.R;
import com.example.infreminder.adapter.CalendarReminderListAdapter;
import com.example.infreminder.adapter.ReminderListAdapter;
import com.example.infreminder.database.DatabaseAccess;
import com.example.infreminder.database.ReminderDao;
import com.example.infreminder.database.ReminderDatabase;
import com.example.infreminder.logic.ReminderListLogic;
import com.example.infreminder.logic.interfaces.I_CreateReminderLogic;
import com.example.infreminder.logic.interfaces.I_ReminderListLogic;
import com.example.infreminder.pojo.Reminder;
import com.example.infreminder.view.interfaces.I_ReminderListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class ReminderListView extends Fragment implements I_ReminderListView {
    private I_ReminderListLogic reminderListLogic;
    private RecyclerView recyclerView;
    private ReminderListAdapter reminderListAdapter;
    private WeakReference<ReminderCalendarView> weakReference3;
    private ReminderCalendarView reminderCalendarView;

    @Override
    public ReminderListView getReminderListView() {
        return this;
    }

    @Override
    public void onResume() {
        super.onResume();
        //reminderListLogic.updateReminders();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list,container,false);
        reminderListLogic = new ReminderListLogic(this);
        recyclerView = view.findViewById(R.id.recycler_list_reminder);
        reminderListLogic.updateReminders();
        reminderListAdapter = new ReminderListAdapter(getContext(), new ArrayList<Reminder>(), this::onItemLongClickListener);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(reminderListAdapter);
        return view;
    }

    public boolean onItemLongClickListener(int position ) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());
        dialogBuilder.setMessage(R.string.confirm_clear_reminder);
        dialogBuilder.setPositiveButton(R.string.yes, (dialogInterface, i) -> {
            /* Remove from database */
            final Reminder rem = reminderListAdapter.getPositionReminder(position);
            ReminderDao dao = ReminderDatabase.getInstance(getContext()).reminderDao();
            new Thread(() -> dao.deleteReminder(rem)).start();

            /* Remove from adapter */
            reminderListAdapter.removeReminderPosition(position);
        });
        dialogBuilder.setNegativeButton(R.string.no, null);
        dialogBuilder.show();
        return true;
    }

    public ReminderListView(){}

    public void updateList(List<Reminder> reminderList) {
        reminderListAdapter.updateReminders(reminderList);
    }
}
