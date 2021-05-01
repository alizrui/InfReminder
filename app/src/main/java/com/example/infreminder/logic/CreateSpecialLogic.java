package com.example.infreminder.logic;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.net.sip.SipSession;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.infreminder.logic.interfaces.I_CreateSpecialLogic;
import com.example.infreminder.view.interfaces.I_CreateSpecialView;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;


public class CreateSpecialLogic implements I_CreateSpecialLogic {

    private I_CreateSpecialView view;

    public CreateSpecialLogic(I_CreateSpecialView view){
        this.view = view;
    }

    @Override
    public void showDatePickerDialog(Calendar calendar) {

        DatePickerFragment newFragment = DatePickerFragment.newInstance((datePicker, year, month, day) -> {
            calendar.set(Calendar.YEAR,year);
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.DAY_OF_MONTH, day);
        });

        newFragment.show(view.getCreateSpecialView().getActivity().getSupportFragmentManager(), "datePicker");
    }


    public static class DatePickerFragment extends DialogFragment {

        private DatePickerDialog.OnDateSetListener listener;

        public static DatePickerFragment newInstance(DatePickerDialog.OnDateSetListener listener){
            DatePickerFragment fragment = new DatePickerFragment();
            fragment.setListener(listener);
            return fragment;
        }

        public void setListener(DatePickerDialog.OnDateSetListener listener){
            this.listener = listener;
        }

        @NonNull
        @Override
        public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
            Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog d = new DatePickerDialog(getActivity(), listener, year, month, day);
            return d;
        }
    }
}
