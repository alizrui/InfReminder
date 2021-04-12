package com.example.infreminder.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.infreminder.R;

import java.util.List;

public class ReminderListAdapter extends RecyclerView.Adapter<ReminderListAdapter.ViewHolder> {
    Context context;
    List<String> listName;
    List<String> listDate;
    List<String> listHour;


    public ReminderListAdapter(Context c ,List<String> ln, List<String> ld,List<String> lh){
        this.context =c;
        this.listName = ln;
        this.listDate = ld;
        this.listHour = lh;

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
        holder.name.setText(listName.get(position));
        holder.date.setText(listDate.get(position));
        holder.hour.setText(listHour.get(position));

    }


    @Override
    public int getItemCount() {
        return listName.size();
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

            //imageView = view.findViewById(R.id.ivIdReminder);
        }


    }
}
