package com.example.infreminder.fragmentsview;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.infreminder.R;
import com.example.infreminder.database.ReminderDatabase;
import com.example.infreminder.fragmentsview.interfaces.DatePickerFragment;
import com.example.infreminder.reminder.Reminder;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Date;
import java.util.List;

public class CreateReminderFragment extends Fragment {
    TextInputLayout tiDate;
    TextInputEditText tieDate;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_reminder, container, false);
        tiDate = (TextInputLayout) view.findViewById(R.id.prueba);
        tieDate = (TextInputEditText) view.findViewById(R.id.tIDay);
        tiDate.setError("La fecha no tiene el formato correcto");
        Date a = new Date();
        Reminder p = new Reminder("a","b","a",a);
        ReminderDatabase.getInstance(getContext()).reminderDao().addReminder(p);
        List<Reminder> prueba = ReminderDatabase.getInstance(getContext()).reminderDao().getReminders();

        tieDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        return view;
    }
    private void showDatePickerDialog() {
        DatePickerFragment newFragment = DatePickerFragment.newInstance((datePicker, year, month, day) -> {
            // +1 because January is zero
            final String selectedDate = day + "/" + (month + 1) + "/" + year;
            tieDate.setText(selectedDate);
        });
        newFragment.show(getActivity().getSupportFragmentManager(), "datePicker");
    }
}
