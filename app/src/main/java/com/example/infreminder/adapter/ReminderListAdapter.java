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
    List<String> listDescription;


    public ReminderListAdapter(Context c ,List<String> ln, List<String> ld){
        this.context =c;
        this.listName = ln;
        this.listDescription = ld;

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
        holder.description.setText(listDescription.get(position));
    }


    @Override
    public int getItemCount() {
        return listName.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        //ImageView imageView;
        TextView name;
        TextView description;


        public ViewHolder(@NonNull View view) {
            super(view);

            name = view.findViewById(R.id.tvNameReminder);
            description = view.findViewById(R.id.tvDescriptionReminder);
            //imageView = view.findViewById(R.id.ivIdReminder);
        }


    }
}
