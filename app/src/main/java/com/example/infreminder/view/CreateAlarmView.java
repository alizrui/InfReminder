package com.example.infreminder.view;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.infreminder.R;
import com.example.infreminder.dialogs.DialogAlarmDaysFragment;
import com.example.infreminder.logic.CreateAlarmLogic;
import com.example.infreminder.logic.interfaces.I_CreateAlarmLogic;
import com.example.infreminder.view.interfaces.I_CreateAlarmView;

import java.util.ArrayList;
import java.util.Calendar;


public class CreateAlarmView extends Fragment implements I_CreateAlarmView {

    private I_CreateAlarmLogic createAlarmLogic;

    /* Views */
    private TimePicker tpTime;
    private EditText etName;
    private EditText etDes;
    private RadioButton rbOnlyOnce;
    private RadioButton rbEveryDay;
    private RadioButton rbSelectDays;

    private Button bNewAlarm;

    /* */
    private ArrayList<Integer> daysSelected;


    public CreateAlarmView() { }

    @Override
    public CreateAlarmView getCreateAlarmView() {
        return this;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_alarm, container, false);

        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {

            }
        };

        createAlarmLogic = new CreateAlarmLogic(this);

        /**
        * Keep a reference to views in the create alarm fragment
        * */
        tpTime = view.findViewById(R.id.tpHours);
        etName = view.findViewById(R.id.etName);
        etDes = view.findViewById(R.id.etDescription);
        rbOnlyOnce = view.findViewById(R.id.rbOnlyOnce);
        rbEveryDay = view.findViewById(R.id.rbEveryDay);
        rbSelectDays = view.findViewById(R.id.rbSelectDays);

        bNewAlarm = view.findViewById(R.id.bNewAlarm);

        rbSelectDays.setOnClickListener(v -> {
            openSelectDaysDialog();
        });

        bNewAlarm.setOnClickListener(v -> {
            createAlarm();
        });

        getChildFragmentManager().setFragmentResultListener("requestDays", this,
                (requestKey, result) -> daysSelected = result.getIntegerArrayList("days"));

        return view;
    }

    private void openSelectDaysDialog(){
        DialogAlarmDaysFragment dialog = new DialogAlarmDaysFragment();
        dialog.show(getChildFragmentManager(),null);
    }

    public void createAlarm() {

        String name = etName.getText().toString();
        String desc = etDes.getText().toString();
        int hour = tpTime.getHour();
        int min = tpTime.getMinute();
        ArrayList<Integer> days = new ArrayList<>();
        //boolean soundOnce = false;

        if (rbOnlyOnce.isChecked()) {
            Calendar rightNow = Calendar.getInstance();
            if (rightNow.get(Calendar.HOUR_OF_DAY) > hour ||
                    (rightNow.get(Calendar.HOUR_OF_DAY) == hour && rightNow.get(Calendar.MINUTE) >= min)) {
                days.add(rightNow.get(Calendar.DAY_OF_WEEK) + 1);
            } else {
                days.add(rightNow.get(Calendar.DAY_OF_WEEK));
            }
//            soundOnce = true;

        } else if (rbEveryDay.isChecked()) {
            for(int i = 1; i <= 7; i++) days.add(i);

        } else if(rbSelectDays.isChecked()){
            days = daysSelected;
            if(daysSelected == null || daysSelected.isEmpty()) {
                Toast.makeText(getContext(), R.string.days_error, Toast.LENGTH_SHORT).show();
                return;
            }
        } else {
            Toast.makeText(getContext(), R.string.days_error, Toast.LENGTH_SHORT).show();
            return;
        }


        // Añadir alarma a BD (logica)
        createAlarmLogic.createAlarm(hour, min, name, desc, days); // falta meter otras características

    }


}
