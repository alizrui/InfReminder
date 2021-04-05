package com.example.infreminder.fragmentsview;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.infreminder.R;
import com.example.infreminder.dialogs.DialogAlarmDaysFragment;
import com.example.infreminder.fragmentpresenter.interfaces.I_CreateAlarmPresenter;
import com.example.infreminder.fragmentsview.interfaces.I_CreateAlarmFragment;

import java.util.ArrayList;

public class CreateAlarmFragment extends Fragment implements I_CreateAlarmFragment {

    private I_CreateAlarmPresenter i_createAlarmPresenter;
    private I_CreateAlarmPresenter presenter;


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


    public CreateAlarmFragment() { }


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

        rbSelectDays.setOnClickListener(v -> {
            openSelectDaysDialog();
        });

        return view;
    }

    private void openSelectDaysDialog(){
        DialogAlarmDaysFragment dialog = new DialogAlarmDaysFragment();
        dialog.show(getChildFragmentManager(),null);

    }
}
