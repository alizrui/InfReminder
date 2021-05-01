package com.example.infreminder.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TimePicker;

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

import java.util.ArrayList;

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

    public CreateSpecialView() { }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_special, container, false);

        logic = new CreateSpecialLogic(this);

        /* Get the views */
        tpTime = view.findViewById(R.id.tpSpecial);
        etName = view.findViewById(R.id.etNameSpecial);

        rbOnlyDay = view.findViewById(R.id.rbOnlyOnceSpecial);
        rbSelectDays = view.findViewById(R.id.rbSelectDaysSpecial);

        bNewSpecial = view.findViewById(R.id.bNewSpecial);

        /* Listeners */
        rbOnlyDay.setOnClickListener(v -> {

        });

        rbSelectDays.setOnClickListener(v ->{
            openSelectDaysDialog();
        });

        bNewSpecial.setOnClickListener(v-> {
            createSpecial();
        }
        );

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

    private void createSpecial(){

    }
}
