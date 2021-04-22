package com.example.infreminder.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.infreminder.R;
import com.example.infreminder.adapter.ReminderListAdapter;
import com.example.infreminder.database.DatabaseAccess;
import com.example.infreminder.reminder.Reminder;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Database;


import java.util.ArrayList;
import java.util.List;

public class ReminderListView extends Fragment {
    private RecyclerView recyclerView;
    private ReminderListAdapter reminderListAdapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_list,container,false);
        recyclerView = view.findViewById(R.id.recycler_list_reminder);
        DatabaseAccess access = new DatabaseAccess(this);
        access.loadReminders();
        reminderListAdapter = new ReminderListAdapter(getContext(), new ArrayList<Reminder>());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(reminderListAdapter);
        return  view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    public ReminderListView(){}

    public void updateList(List<Reminder> reminderList) {
        reminderListAdapter.updateReminders(reminderList);
    }


}
