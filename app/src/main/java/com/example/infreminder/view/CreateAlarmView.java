package com.example.infreminder.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.infreminder.R;
import com.example.infreminder.dialogs.DialogAlarmDaysFragment;
import com.example.infreminder.logic.interfaces.I_CreateAlarmLogic;
import com.example.infreminder.view.interfaces.I_CreateAlarmView;

import java.util.ArrayList;
import java.util.Calendar;


public class CreateAlarmView extends Fragment implements I_CreateAlarmView {

    private I_CreateAlarmLogic i_createAlarmLogic;
    private I_CreateAlarmLogic presenter;


    /* Views */
    private TimePicker tpTime;
    private EditText etName;
    private EditText etDes;
    private RadioButton rbOnlyOnce;
    private RadioButton rbEveryDay;
    private RadioButton rbSelectDays;

    private Button bNewAlarm;

    /* */
    private ArrayList<String> daysSelected;

    public CreateAlarmView() { }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_alarm, container, false);

        /*
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
                (requestKey, result) -> daysSelected = result.getStringArrayList("days"));

        return view;
    }

    private void openSelectDaysDialog(){
        DialogAlarmDaysFragment dialog = new DialogAlarmDaysFragment();
        dialog.show(getChildFragmentManager(),null);
    }

    public void createAlarm() {
        String name = etName.getText().toString();
        String desc = etDes.getText().toString();
        //int hour = tpTime.getCurrentHour();
        int hour = tpTime.getHour();
        int min = tpTime.getMinute();
        ArrayList<String> days = new ArrayList<>();
        //boolean soundOnce = false;

        if (rbOnlyOnce.isChecked()) {
            Calendar rightNow = Calendar.getInstance();
            if (rightNow.get(Calendar.HOUR_OF_DAY) > hour ||
                    (rightNow.get(Calendar.HOUR_OF_DAY) == hour && rightNow.get(Calendar.MINUTE) >= min)) {
                days.add(dayTranslator(rightNow.get(Calendar.DAY_OF_WEEK) + 1));
            } else {
                days.add(dayTranslator(rightNow.get(Calendar.DAY_OF_WEEK)));
            }
//            soundOnce = true;

        } else if (rbEveryDay.isChecked()) {
            for(int i = 1; i <= 7; i++) days.add(dayTranslator(i));
        } else if(rbSelectDays.isChecked()){
            days = daysSelected;
        }

        // Añadir alarma a BD

    }

    private String dayTranslator(int day){ // esto debería ir en la lógica? cual es la lógica?
        String res = "";
        switch(day){
            case Calendar.MONDAY: // 2
                res ="Monday";
                break;
            case Calendar.TUESDAY: // 3
                res = "Tuesday";
                break;
            case Calendar.WEDNESDAY: // 4
                res = "Wednesday";
                break;
            case Calendar.THURSDAY: // 5
                res = "Thursday";
                break;
            case Calendar.FRIDAY: // 6
                res = "Friday";
                break;
            case Calendar.SATURDAY: // 7
                res = "Saturday";
                break;
            case Calendar.SUNDAY: // 1
                res = "Sunday";
                break;
            default:
                res = "";
                break;
        }
        return res;
    }


}
