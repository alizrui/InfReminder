package com.example.infreminder.view;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;

import com.example.infreminder.R;
import com.example.infreminder.dialogs.DialogAlarmDaysFragment;
import com.example.infreminder.logic.CreateAlarmLogic;
import com.example.infreminder.logic.interfaces.I_CreateAlarmLogic;
import com.example.infreminder.view.interfaces.I_CreateAlarmView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class CreateAlarmView extends Fragment implements I_CreateAlarmView {

    private I_CreateAlarmLogic createAlarmLogic;

    /* Views */
    private TimePicker tpTime;
    private EditText etName;
    private EditText etDes;

    private SwitchCompat scFijo;

    private RadioButton rbSelectDays;

    private RadioGroup rgDays;


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
//        listeners.add((FragmentListener) savedInstanceState.getSerializable("listener"));

        /**
        * Keep a reference to views in the create alarm fragment
        * */
        tpTime = view.findViewById(R.id.tpHours);
        etName = view.findViewById(R.id.etName);
        etDes = view.findViewById(R.id.etDescription);

        scFijo = view.findViewById(R.id.scFijo);

        rbSelectDays = view.findViewById(R.id.rbSelectDays);

        rgDays = view.findViewById(R.id.rgRepeatEvery);


        bNewAlarm = view.findViewById(R.id.bNewAlarm);

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

        tpTime.setMinute(tpTime.getMinute() + 1);

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

    @SuppressLint("NonConstantResourceId")
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

        switch(rgDays.getCheckedRadioButtonId()){
            case R.id.rbOnlyOnce:
                createAlarmLogic.repeatOnlyOnce(hour, min, days);
                jsonObject.put("only_once", true);
                break;

            case R.id.rbEveryDay:
                createAlarmLogic.repeatEveryDay(days);
                jsonObject.put("only_zonce", false);
                break;

            case R.id.rbSelectDays:
                days = daysSelected;
                if(daysSelected == null || daysSelected.isEmpty()) {
                    Toast.makeText(getContext(), R.string.days_error, Toast.LENGTH_SHORT).show();
                    return;
                }
                jsonObject.put("only_once", false);
                break;

            default:
                Toast.makeText(getContext(), R.string.days_error, Toast.LENGTH_SHORT).show();
                return;

        }

        int repeatEvery = 0;

        if(scFijo.isChecked()){
            repeatEvery = -1;
        }

        jsonObject.put("repeat_every", repeatEvery);

        // Añadir alarma a BD (logica)
        createAlarmLogic.createAlarm(hour, min, name, jsonObject, days);
        Toast.makeText(getContext(), R.string.alarm_created, Toast.LENGTH_SHORT).show();

        getActivity().onBackPressed();
    }
}
