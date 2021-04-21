package com.example.infreminder.preference;

import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.preference.DialogPreference;
import androidx.preference.PreferenceDialogFragmentCompat;

import com.example.infreminder.R;

public class TimePreferenceDialogFragmentCompat extends PreferenceDialogFragmentCompat {

    private TimePicker timePicker;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        DialogPreference preference = getPreference();

        preference.setNegativeButtonText("Cancelar");
        preference.setPositiveButtonText("Aceptar");
        super.onCreate(savedInstanceState);
    }


    public static TimePreferenceDialogFragmentCompat newInstance(String key) {
        final TimePreferenceDialogFragmentCompat
                fragment = new TimePreferenceDialogFragmentCompat();
        final Bundle b = new Bundle(1);
        b.putString(ARG_KEY, key);
        fragment.setArguments(b);

        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return super.onCreateDialog(savedInstanceState);
    }

    @Override
    protected void onBindDialogView(View view) {
        super.onBindDialogView(view);

        timePicker = (TimePicker) view.findViewById(R.id.edit);

        if (timePicker == null) {
            throw new IllegalStateException("Dialog view must contain a TimePicker with id 'edit'");
        }

        Integer minutesAfterMidnight = null;
        DialogPreference preference = getPreference();

        if (preference instanceof TimePreference) {
            minutesAfterMidnight = ((TimePreference) preference).getTime();
        }

        if (minutesAfterMidnight != null) {
            int hours = minutesAfterMidnight / 60;
            int minutes = minutesAfterMidnight % 60;
            boolean is24hour = DateFormat.is24HourFormat(getContext());

            timePicker.setIs24HourView(is24hour);
            timePicker.setHour(hours);
            timePicker.setMinute(minutes);
        }
    }


    @Override
    public void onDialogClosed(boolean positiveResult) {
        if (positiveResult) {
            int hours;
            int minutes;
            if (Build.VERSION.SDK_INT >= 23) {
                hours = timePicker.getHour();
                minutes = timePicker.getMinute();
            } else {
                hours = timePicker.getCurrentHour();
                minutes = timePicker.getCurrentMinute();
            }

            int minutesAfterMidnight = (hours * 60) + minutes;
            DialogPreference preference = getPreference();

            if (preference instanceof TimePreference) {
                TimePreference timePreference = ((TimePreference) preference);
                if (timePreference.callChangeListener(minutesAfterMidnight)) {
                    timePreference.setTime(minutesAfterMidnight);
                }
            }
        }
    }
}