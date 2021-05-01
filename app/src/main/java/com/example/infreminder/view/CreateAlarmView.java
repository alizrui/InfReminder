package com.example.infreminder.view;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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

import org.json.JSONException;
import org.json.JSONObject;

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

    private RadioButton rbSimple;
    private RadioButton rbFixed;
    private RadioButton rbReply;

    private RadioGroup rgType;

    private Button bNewAlarm;

    /* */
    private ArrayList<String> daysSelected;


    public CreateAlarmView() { }

    @Override
    public CreateAlarmView getCreateAlarmView() {
        return this;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_alarm, container, false);

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


        rbSimple = view.findViewById(R.id.rbSimple);
        rbFixed = view.findViewById(R.id.rbFixed);
        rbReply = view.findViewById(R.id.rbReply);
        rgType = view.findViewById(R.id.rgType);



        /* Listeners*/
        rbSelectDays.setOnClickListener(v -> {
            openSelectDaysDialog();
        });

        bNewAlarm.setOnClickListener(v -> {
            try {
                createAlarm();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });

        getChildFragmentManager().setFragmentResultListener("requestDays", this,
                (requestKey, result) -> daysSelected = result.getStringArrayList("days"));

        if (savedInstanceState != null){
            daysSelected = savedInstanceState.getStringArrayList("days");
        }

        return view;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putStringArrayList("days", daysSelected);
    }

    private void openSelectDaysDialog(){
        DialogAlarmDaysFragment dialog = new DialogAlarmDaysFragment();
        dialog.show(getChildFragmentManager(),null);
    }

    public void createAlarm() throws JSONException {

        String name = etName.getText().toString();
        if(name.isEmpty()){
            Toast.makeText(getContext(), R.string.name_error, Toast.LENGTH_SHORT).show();
            return;
        }

        int hour = tpTime.getHour();
        int min = tpTime.getMinute();
        ArrayList<String> days = new ArrayList<>();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("desc", etDes.getText());

        /* Cambiar por radio group selection */
        if (rbOnlyOnce.isChecked()) {

            createAlarmLogic.repeatOnlyOnce(hour, min, days);
            jsonObject.put("only_once", true);

        } else if (rbEveryDay.isChecked()) {

            createAlarmLogic.repeatEveryDay(days);
            jsonObject.put("only_once", false);;

        } else if(rbSelectDays.isChecked()){
            days = daysSelected;
            if(daysSelected == null || daysSelected.isEmpty()) {
                Toast.makeText(getContext(), R.string.days_error, Toast.LENGTH_SHORT).show();
                return;
            }
            jsonObject.put("only_once", false);
        } else {
            Toast.makeText(getContext(), R.string.days_error, Toast.LENGTH_SHORT).show();
            return;
        }


        int repeatEvery = 0;
        String replyText = "";

        switch (rgType.getCheckedRadioButtonId()) {
            case R.id.rbSimple : break;
            case R.id.rbFixed :  repeatEvery = -1; break;
            case R.id.rbReply :
                repeatEvery = -1;
                replyText = "";
                Toast.makeText(getContext(), "Esto no fundiona", Toast.LENGTH_LONG).show();
                break;

        }

        jsonObject.put("repeat_every", repeatEvery);
        jsonObject.put("reply_text", replyText);

        // Añadir alarma a BD (logica)
        createAlarmLogic.createAlarm(hour, min, name, jsonObject, days);
        Toast.makeText(getContext(), R.string.alarm_created, Toast.LENGTH_SHORT).show();
        getActivity().onBackPressed();
    }
}
