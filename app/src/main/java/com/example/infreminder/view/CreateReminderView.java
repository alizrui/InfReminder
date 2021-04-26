package com.example.infreminder.view;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.infreminder.R;
import com.example.infreminder.logic.CreateReminderLogic;
import com.example.infreminder.logic.interfaces.I_CreateReminderLogic;
import com.example.infreminder.view.interfaces.I_CreateReminderView;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.Calendar;

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
