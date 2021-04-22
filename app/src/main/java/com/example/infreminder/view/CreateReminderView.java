package com.example.infreminder.view;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.example.infreminder.R;
import com.example.infreminder.database.ReminderDao;
import com.example.infreminder.database.ReminderDatabase;
import com.example.infreminder.reminder.Reminder;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CreateReminderView extends Fragment {
    private TextInputLayout tiDate;
    private TextInputEditText tieDate;
    private TextInputEditText tieName;
    private TextInputEditText tieDescription;
    private Button bAccept;
    private Button bCancel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_reminder, container, false);
        tiDate = (TextInputLayout) view.findViewById(R.id.prueba);
        tieDate = (TextInputEditText) view.findViewById(R.id.tIDay);
        tieName = (TextInputEditText) view.findViewById(R.id.tIName);
        tieDescription = (TextInputEditText) view.findViewById(R.id.tIDescription);
        bAccept = (Button) view.findViewById(R.id.bAccept);
        bCancel =(Button) view.findViewById(R.id.bCancel);

        tieDate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                bAccept.setEnabled(fieldEmpty());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        tieName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                bAccept.setEnabled(fieldEmpty());

            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        tieDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        bAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE);
                ArrayList<String> days2 = new ArrayList<>();
                days2.add("Lunes");

                new Thread(() -> {

                    Reminder reminder = new Reminder(
                            tieName.getText().toString(),
                            tieDescription.toString(),days2,
                            Calendar.getInstance());
                    ReminderDatabase.getInstance(getContext()).reminderDao().addReminder(reminder);

                }).start();

                getActivity().onBackPressed();

            }
        });

        bCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle(R.string.cancel);
                builder.setMessage(R.string.title_cancel);
                builder.setPositiveButton(R.string.accept, (dialog, which) -> {
                    getActivity().onBackPressed();
                });
                builder.setNegativeButton(R.string.cancel, null);
                AlertDialog dialog = builder.create();
                dialog.show();

            }
        });

        return view;
    }
    private boolean fieldEmpty() {
        String s1 = tieDate.getText().toString();
        String s2 = tieName.getText().toString();
        return (s1.isEmpty() || s2.isEmpty()) ? false : true;
    }
    private void showDatePickerDialog() {
        DatePickerFragment newFragment = DatePickerFragment.newInstance((datePicker, year, month, day) -> {
            String s1 = (day / 10 < 1) ? "0": "";
            String s2 = ((month + 1)  / 10 < 1) ? "0": "";
            // +1 because January is zero
            final String selectedDate = s1 + day + "/" + s2 + (month + 1) + "/" + year;
            tieDate.setText(selectedDate);
        });
        newFragment.show(getActivity().getSupportFragmentManager(), "datePicker");
    }
    public static class DatePickerFragment extends DialogFragment {

        private DatePickerDialog.OnDateSetListener listener;

        public static DatePickerFragment newInstance(DatePickerDialog.OnDateSetListener listener) {
            DatePickerFragment fragment = new DatePickerFragment();
            fragment.setListener(listener);
            return fragment;
        }
        public void setListener(DatePickerDialog.OnDateSetListener listener) {
            this.listener = listener;
        }
        @Override
        @NonNull
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            return new DatePickerDialog(getActivity(), listener, year, month, day);
        }
    }
}
