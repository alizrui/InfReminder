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
    private TextView  textVReminder,areReminders;

    /**
     * Se cargan los diferentes elementos de la UI y se añade un listener al calendar.
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calendar, container, false);
        calendar = view.findViewById(R.id.cvMainCalendar);
        recyclerView = view.findViewById(R.id.rvEventesOfDay);
        textVReminder = view.findViewById(R.id.textVReminder);
        areReminders= view.findViewById(R.id.areThereReminders);

        // El usuario no pueda escoger en el calendario días anteriores al actual
        calendar.setMinDate(System.currentTimeMillis() - 1000);

        //Cogemos el día actual y lo mostramos por pantalla
        Calendar dte= Calendar.getInstance();
        SimpleDateFormat sdf =  new SimpleDateFormat("dd/M/yyyy");
        String curDate = sdf.format(dte.getTime());
        textVReminder.setText( getString(R.string.reminder_of_day) + curDate);

        // date = fecha sin horas
        date = Instant.now().truncatedTo(ChronoUnit.DAYS);

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
                    textVReminder.setText(getString(R.string.reminder_of_day) + day + "/" + (month + 1) + "/" + year);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                load();
            }
        });
        return view;

    }

    /**
     * Accede a la base de datos.
     */

    public void load(){
        access = new DatabaseAccess(this,null,this);
        access.loadReminders();
    }

    /**
     * Actualiza la lista y se lo pasa al adapter de calendar.
     * @param reminderList lista obtenida de la base de datos
     */
    public void updateList(List<Reminder> reminderList) {
        this.reminders = reminderList;
        calendarReminderListAdapter = new CalendarReminderListAdapter(getContext(), reminders);
        // si no existen recordatorios para esa fecha entonces escribimos que no hay recordatorios en la pantalla
        if (reminderList.isEmpty()){
            areReminders.setVisibility(View.VISIBLE);
            areReminders.setText(getString(R.string.no_reminder));
        }else {
         // si no, invisibilizamos el texto y pasamos la lista al adapter
            areReminders.setVisibility(View.INVISIBLE);}
            recyclerView.setAdapter(calendarReminderListAdapter);
    }

}
