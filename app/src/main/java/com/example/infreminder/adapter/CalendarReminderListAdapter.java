package com.example.infreminder.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.infreminder.R;
import com.example.infreminder.view.ReminderCalendarView;
import com.example.infreminder.pojo.Reminder;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class CalendarReminderListAdapter extends RecyclerView.Adapter<CalendarReminderListAdapter.ViewHolder> {
    private List<Reminder> reminderList;
    Context context;
    public CalendarView calendarView;

    /**
     * @param c
     * @param reminderList
     */
    public CalendarReminderListAdapter(Context c , List<Reminder> reminderList){
        this.context = c;
        this.reminderList = reminderList;
    }
/*
    public void updateReminders(List<Reminder> reminders) {
        this.reminderList = reminders;
        notifyDataSetChanged();
    }

 */


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.calendar_list_row, parent, false);
        CalendarReminderListAdapter.ViewHolder holder = new ViewHolder(view);
        calendarView =  view.findViewById(R.id.cvMainCalendar);
        return holder;
    }



    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Reminder reminder = reminderList.get(position);
        Calendar calendar = reminder.getDate();
        /*
        int currentYear = calendar.get(Calendar.YEAR);
        int currentMonth = calendar.get(Calendar.MONTH) ;
        int currentDay = calendar.get(Calendar.DAY_OF_MONTH);
        ArrayList<String> days = reminder.getDays();
        /*
        boolean isEmpty = true;
        for (int i = 0; i < days.size(); i++){
            if (days.get(i) != "" || days.get(i) != null){
                isEmpty =  false;
            }
        }

         */
        //if(ReminderCalendarView.day == currentDay && ReminderCalendarView.month == currentMonth && ReminderCalendarView.year == currentYear){
           // if (isEmpty){
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
                holder.name.setText(reminder.getName());
                holder.hour.setText(simpleDateFormat.format(calendar.getTime()));
            //}
        //}
    }

    @Override
    public int getItemCount() {
        return reminderList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView hour;

        public ViewHolder(@NonNull View view) {
            super(view);
            name = view.findViewById(R.id.tvNameCalendarReminder);
            hour = view.findViewById(R.id.tvCalendarDate);

        }
    }
}

