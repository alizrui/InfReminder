package com.example.infreminder.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.infreminder.R;
import com.example.infreminder.pojo.Reminder;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class ReminderListAdapter extends RecyclerView.Adapter<ReminderListAdapter.ViewHolder> {
    Context context;
    private List<Reminder> reminders;
    private final OnItemLongClickListener intLongListener;


    /**
     * @param c
     * @param reminders
     * @param intLongListener
     */
    public ReminderListAdapter(Context c ,List<Reminder> reminders, OnItemLongClickListener intLongListener) {
        this.context = c;
        this.reminders = reminders;
        this.intLongListener = intLongListener;
    }

    public void updateReminders(List<Reminder> reminders) {
        this.reminders = reminders;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.reminder_list_row, parent, false);
        ReminderListAdapter.ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Reminder reminder = reminders.get(position);
        Calendar calendar = reminder.getDate();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        holder.name.setText(reminder.getName());
        holder.date.setText(sdf.format(calendar.getTime()));
        holder.hour.setText("");
    }

    public Reminder getPositionReminder(int pos){
        return reminders.get(pos);
    }


    @Override
    public int getItemCount() {
        return reminders.size();
    }

    public void removeReminderPosition (int pos){
        reminders.remove(pos);
        notifyItemRemoved(pos);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView date;
        TextView hour;


        public ViewHolder(@NonNull View view) {
            super(view);

            name = view.findViewById(R.id.tvNameReminder);
            date = view.findViewById(R.id.tvDate);
            hour = view.findViewById(R.id.tvHour);


            view.setOnLongClickListener(v -> {
                intLongListener.onItemLongClickListener(getAdapterPosition());
                return true;
            });
        }
    }

    public interface OnItemLongClickListener {
        void onItemLongClickListener(int position);
    }

}
