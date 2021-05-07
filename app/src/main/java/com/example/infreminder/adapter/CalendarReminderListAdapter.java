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

import org.w3c.dom.Text;

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
     * Constructor para mostrar la lista de reminders filtrado por el calendario.
     *
     * @param c contexto fragments
     * @param reminderList lista de reminders
     */
    public CalendarReminderListAdapter(Context c , List<Reminder> reminderList){
        this.context = c;
        this.reminderList = reminderList;
    }

    /**
     * Definición del xml de la fila de la lista.
     *
     * @param parent
     * @param viewType
     * @return el viewHolder con el view de la fila asociado
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.calendar_list_row, parent, false);
        CalendarReminderListAdapter.ViewHolder holder = new ViewHolder(view);
        calendarView =  view.findViewById(R.id.cvMainCalendar);
        return holder;
    }

    /**
     * Bindeo de los componentes de la lista con los atributos que se quieren mostrar.
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Reminder reminder = reminderList.get(position);
        Calendar calendar = reminder.getDate();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");

        holder.name.setText(reminder.getName());
        holder.hour.setText(simpleDateFormat.format(calendar.getTime()));

    }

    /**
     * Longitud de la lista.
     *
     * @return size de la lista
     */
    @Override
    public int getItemCount() {
        return reminderList.size();
    }

    /**
     * Clase que extiende del ViewHolder para mostar el reminder en las filas.
     */
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView hour;
        /**
         * Constructor del ViewHolder personalizado para este adapter
         * @param view
         */
        public ViewHolder(@NonNull View view) {
            super(view);
            name = view.findViewById(R.id.tvNameCalendarReminder);
            hour = view.findViewById(R.id.tvCalendarDate);

        }
    }
}

