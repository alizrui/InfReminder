package com.example.infreminder.view;

import android.annotation.SuppressLint;
import android.os.Bundle;
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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.infreminder.R;
import com.example.infreminder.dialogs.DialogAlarmDaysFragment;
import com.example.infreminder.logic.CreateAlarmLogic;
import com.example.infreminder.logic.CreateSpecialLogic;
import com.example.infreminder.logic.interfaces.I_CreateAlarmLogic;
import com.example.infreminder.logic.interfaces.I_CreateSpecialLogic;
import com.example.infreminder.view.interfaces.I_CreateSpecialView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

public class CreateSpecialView extends Fragment implements I_CreateSpecialView {

    private I_CreateSpecialLogic logic;

    /* Views */
    private TimePicker tpTime;
    private EditText etName;

    private RadioButton rbSelectDays;
    private RadioButton rbOnlyDay;

    private RadioGroup rgDays;

    private Button bNewSpecial;

    private ArrayList<String> daysSelected;

    private Calendar date = Calendar.getInstance();

    public CreateSpecialView() { }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_special, container, false);

        logic = new CreateSpecialLogic(this);


        /* Get the views */
        tpTime = view.findViewById(R.id.tpSpecial);
        etName = view.findViewById(R.id.etNameSpecial);

        rgDays = view.findViewById(R.id.rgSpecial);

        rbOnlyDay = view.findViewById(R.id.rbOnlyOnceSpecial);
        rbSelectDays = view.findViewById(R.id.rbSelectDaysSpecial);

        bNewSpecial = view.findViewById(R.id.bNewSpecial);

        /* Listeners */
        rbOnlyDay.setOnClickListener(v -> {
            logic.showDatePickerDialog(date);
        });

        rbSelectDays.setOnClickListener(v ->{
            openSelectDaysDialog();
        });

        bNewSpecial.setOnClickListener(v-> {
            try {
                createSpecial();
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
    private void createSpecial() throws JSONException {
        String name = etName.getText().toString();
        if(name.isEmpty()){
            Toast.makeText(getContext(), R.string.name_error, Toast.LENGTH_SHORT).show();
            return;
        }

        int hour = tpTime.getHour();
        int min = tpTime.getMinute();

        ArrayList<String> days = new ArrayList<>();
        JSONObject jsonObject = new JSONObject();

        /* Metiendo campos */ jsonObject.put("desc", "");
        jsonObject.put("reply_text", "");
        jsonObject.put("repeat_every", 0);

        switch(rgDays.getCheckedRadioButtonId()){
            case R.id.rbOnlyOnceSpecial:
                jsonObject.put("only_once", true);
                date.set(Calendar.HOUR_OF_DAY, hour);
                date.set(Calendar.MINUTE, min);
                logic.createSpecialReminder(name, date,jsonObject);
                Toast.makeText(getContext(), R.string.special_created, Toast.LENGTH_SHORT).show();
                getActivity().onBackPressed();
                break;
            case R.id.rbSelectDaysSpecial:
                jsonObject.put("only_once", false);
                days = daysSelected;
                logic.createSpecialAlarm(hour, min, name,days,jsonObject);
                Toast.makeText(getContext(), R.string.special_created, Toast.LENGTH_SHORT).show();
                getActivity().onBackPressed();
                break;
            default:
                Toast.makeText(getContext(), R.string.days_error, Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public CreateSpecialView getCreateSpecialView() {
        return this;
    }
}
