package com.example.infreminder.adapter;

import android.content.Context;
import android.util.JsonReader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.infreminder.R;
import com.example.infreminder.Utils.Utils;
import com.example.infreminder.pojo.Reminder;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class ReminderListAdapter extends RecyclerView.Adapter<ReminderListAdapter.ViewHolder> {
    Context context;
    private List<Reminder> reminders;
    private final OnItemLongClickListener intLongListener;

    /**
     * Constructor del adapter de la lista principal de todos los reminders de la aplicación.
     *
     * @param c contexto del fragment
     * @param reminders lista de reminders
     * @param intLongListener interfaz para la acción de borrar reminder
     */
    public ReminderListAdapter(Context c ,List<Reminder> reminders, OnItemLongClickListener intLongListener) {
        this.context = c;
        this.reminders = reminders;
        this.intLongListener = intLongListener;
    }

    /**
     * Cambia la lista del adapter por la que recibe.
     *
     * @param reminders lista de reminders
     */
    public void updateReminders(List<Reminder> reminders) {
        this.reminders = reminders;
        notifyDataSetChanged();
    }

    /**
     * Define el xml de la fila que se va a mostrar.
     *
     * @param parent
     * @param viewType
     * @return viewHolder con la fila asociada
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.reminder_list_row, parent, false);
        ReminderListAdapter.ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    /**
     * Bindeo de la información con los conponentes de la fila que se muestra.
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Reminder reminder = reminders.get(position);
        try {
            JSONObject jsonObject = Utils.stringToJson(reminder.getFeatures());
            Calendar calendar = reminder.getDate();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            holder.name.setText(reminder.getName());
            holder.date.setText(sdf.format(calendar.getTime()));
            holder.description.setText(jsonObject.get("desc").toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * Devulve el reminder de la posición de la lista.
     *
     * @param pos de la lista
     * @return reminder de la posición dada
     */
    public Reminder getPositionReminder(int pos){
        return reminders.get(pos);
    }

    /**
     * Longitud de la lista.
     *
     * @return int del size de la lista
     */
    @Override
    public int getItemCount() {
        return reminders.size();
    }

    /**
     * Borra la posición de la lista.
     *
     * @param pos posición que se quiere borrar
     */
    public void removeReminderPosition (int pos){
        reminders.remove(pos);
        notifyItemRemoved(pos);
    }

    /**
     * Clase para mostrar en el adapter
     */
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView date;
        TextView description;

        /**
         * Constructor del ViewHolder de este adapter donde también se define la acción de mantener pulsado.
         *
         * @param view
         */
        public ViewHolder(@NonNull View view) {
            super(view);

            name = view.findViewById(R.id.tvNameReminder);
            date = view.findViewById(R.id.tvDate);
            description = view.findViewById(R.id.tvDescription);


            view.setOnLongClickListener(v -> {
                intLongListener.onItemLongClickListener(getAdapterPosition());
                return true;
            });
        }
    }

    /**
     * Interfaz para la acción de mantener pulsado.
     */
    public interface OnItemLongClickListener {
        void onItemLongClickListener(int position);
    }

}
