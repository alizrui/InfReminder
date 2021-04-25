package com.example.infreminder.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.infreminder.R;
import com.example.infreminder.reminder.Reminder;

import java.util.List;

public class CalendarReminderListAdapter extends RecyclerView.Adapter<CalendarReminderListAdapter.ViewHolder> {
    private List<Reminder> reminderList;
    Context context;

    public CalendarReminderListAdapter(Context c , List<Reminder> reminderList){
        this.context = c;
        this.reminderList = reminderList;

    }


    @NonNull
    @Override
    public CalendarReminderListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull CalendarReminderListAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        //ImageView imageView;
        TextView name;
        TextView date;
        TextView hour;


        public ViewHolder(@NonNull View view) {
            super(view);

            name = view.findViewById(R.id.tvNameReminder);
            date = view.findViewById(R.id.tvDate);
            hour = view.findViewById(R.id.tvHour);

        }
    }
}

