package com.example.infreminder.view;

import android.os.Build;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.infreminder.R;
import com.example.infreminder.adapter.CalendarReminderListAdapter;
import com.example.infreminder.adapter.ReminderListAdapter;
import com.example.infreminder.database.DatabaseAccess;
import com.example.infreminder.database.ReminderDatabase;
import com.example.infreminder.pojo.Reminder;
//import com.example.infreminder.reminder.Reminder;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ReminderCalendarView extends Fragment {
    private CalendarView calendar;
    private List<Reminder> reminders = new ArrayList<Reminder>();
    private RecyclerView recyclerView;
    private CalendarReminderListAdapter calendarReminderListAdapter;
    public static Instant date ;
    private DatabaseAccess access;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calendar, container, false);
        calendar = view.findViewById(R.id.cvMainCalendar);
        recyclerView = view.findViewById(R.id.rvEventesOfDay);
        calendar.setMinDate(System.currentTimeMillis() - 1000);

        // date = fecha sin horas
        date = Instant.now().truncatedTo(ChronoUnit.DAYS);
        access = new DatabaseAccess(this,null,this);
        load();
        calendarReminderListAdapter = new CalendarReminderListAdapter(getContext(),new ArrayList<>() );
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(calendarReminderListAdapter);

        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int day) {
                try {
                    date = new SimpleDateFormat("dd/M/yyyy").parse(day + "/" + (month + 1) + "/" + year).toInstant();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                load();
            }
        });
        return view;

    }

    public void load(){
        access = new DatabaseAccess(this,null,this);;
        access.loadReminders();
    }

    public void updateList(List<Reminder> reminderList) {
        this.reminders = reminderList;
        calendarReminderListAdapter = new CalendarReminderListAdapter(getContext(), reminders);
        recyclerView.setAdapter(calendarReminderListAdapter);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
}
