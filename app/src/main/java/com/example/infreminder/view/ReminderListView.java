package com.example.infreminder.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.infreminder.R;
import com.example.infreminder.adapter.ReminderListAdapter;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;

public class ReminderListView extends Fragment {
    private RecyclerView recyclerView;
    private ArrayList<String> nameTitle ;
    private ArrayList<String> rDate ;
    private ArrayList<String> rHour ;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //super.onCreateView(inflater, container, savedInstanceState);
        nameTitle = new ArrayList<>();
        rDate = new ArrayList<>();
        rHour = new ArrayList<>();
        //examples
        for (int i = 0; i<10; i++){
            nameTitle.add("Name Example" + i);
            rDate.add("10/05/200" +i);
            rHour.add("10:0" + i);
        }
        View view = inflater.inflate(R.layout.fragment_list,container,false);
        recyclerView = view.findViewById(R.id.recycler_list_reminder);
        ReminderListAdapter reminderListAdapter = new ReminderListAdapter(getContext(),nameTitle,rDate,rHour);
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


}