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
import androidx.room.Database;

import com.example.infreminder.R;
import com.example.infreminder.database.DatabaseAccess;
import com.example.infreminder.database.ReminderDao;
import com.example.infreminder.database.ReminderDatabase;
import com.example.infreminder.logic.CreateAlarmLogic;
import com.example.infreminder.logic.CreateReminderLogic;
import com.example.infreminder.logic.interfaces.I_CreateAlarmLogic;
import com.example.infreminder.logic.interfaces.I_CreateReminderLogic;
import com.example.infreminder.reminder.Reminder;
import com.example.infreminder.view.interfaces.I_CreateReminderView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CreateReminderView extends Fragment implements I_CreateReminderView {
    private I_CreateReminderLogic createReminderLogic;
    private TextInputEditText tieDate;
    private TextInputEditText tieName;
    private TextInputEditText tieDescription;
    private Button bAccept;
    private Button bCancel;

    @Override
    public CreateReminderView getCreateReminderView() {
        return this;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_reminder, container, false);
        createReminderLogic = new CreateReminderLogic(this);
        tieDate = (TextInputEditText) view.findViewById(R.id.tIDay);
        tieName = (TextInputEditText) view.findViewById(R.id.tIName);
        tieDescription = (TextInputEditText) view.findViewById(R.id.tIDescription);
        bAccept = (Button) view.findViewById(R.id.bAccept);
        bCancel = (Button) view.findViewById(R.id.bCancel);

        /**
         * Apartado listeners para desactivar el botón de aceptar si los datos obligatorios no
         * están rellenos y para abrir un datePicker
         */
        tieDate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String s1 = tieDate.getText().toString();
                String s2 = tieName.getText().toString();
                bAccept.setEnabled(createReminderLogic.fieldEmpty(s1, s2));
            }
            @Override
            public void afterTextChanged(Editable s) { }
        });

        tieName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String s1 = tieDate.getText().toString();
                String s2 = tieName.getText().toString();
                bAccept.setEnabled(createReminderLogic.fieldEmpty(s1, s2));
            }
            @Override
            public void afterTextChanged(Editable s) { }
        });

        tieDate.setOnClickListener(v -> createReminderLogic.showDatePickerDialog(tieDate));

        /**
         * Apartado botones
         */
        bAccept.setOnClickListener(v -> {
            Calendar cal = createReminderLogic.setDate(tieDate.getText().toString());
            createReminderLogic.createReminder(tieName.getText().toString(),
                    tieDescription.toString(), new ArrayList<>(),cal);
            getActivity().onBackPressed();
        });
        bCancel.setOnClickListener(v -> {
            int title = R.string.cancel;
            int message = R.string.title_cancel;
            int positiveButton = R.string.accept;
            int negativeButton = R.string.cancel;
            createReminderLogic.createAlertDialog(title,message,positiveButton,negativeButton);
        });

        return view;
    }
}
