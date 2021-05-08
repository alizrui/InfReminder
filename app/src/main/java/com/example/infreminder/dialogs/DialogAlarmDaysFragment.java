package com.example.infreminder.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.example.infreminder.R;

import java.util.ArrayList;

public class DialogAlarmDaysFragment extends DialogFragment {

    /* Views */
    private Button bOkDays;
    private CheckBox cbMonday;
    private CheckBox cbTuesday;
    private CheckBox cbWednesday;
    private CheckBox cbThursday;
    private CheckBox cbFriday;
    private CheckBox cbSaturday;
    private CheckBox cbSunday;

    public DialogAlarmDaysFragment(){ }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.dialog_alarm_days,null);
        builder.setView(v);

        /*
        * Keep a reference to the views
        * */
        bOkDays = v.findViewById(R.id.bOkDialogAlarm);
        cbMonday = v.findViewById(R.id.cbMonday);
        cbTuesday = v.findViewById(R.id.cbTuesday);
        cbWednesday = v.findViewById(R.id.cbWednesday);
        cbThursday = v.findViewById(R.id.cbThursday);
        cbFriday = v.findViewById(R.id.cbFriday);
        cbSaturday = v.findViewById(R.id.cbSaturday);
        cbSunday = v.findViewById(R.id.cbSunday);

        buttonListener();

        return builder.create();
    }

    /**
     * Listener que añade los días seleccionados a un arraylist.
     *
     * El arraylist se pasa entre fragments.
     */
    private void buttonListener(){
        bOkDays.setOnClickListener(v -> {
            ArrayList<String> daysSelected = new ArrayList<>();

            if (cbSunday.isChecked()) daysSelected.add("1");
            if (cbMonday.isChecked()) daysSelected.add("2");
            if (cbTuesday.isChecked()) daysSelected.add("3");
            if (cbWednesday.isChecked()) daysSelected.add("4");
            if (cbThursday.isChecked()) daysSelected.add("5");
            if (cbFriday.isChecked()) daysSelected.add("6");
            if (cbSaturday.isChecked()) daysSelected.add("7");


            Bundle result = new Bundle();
            result.putStringArrayList("days", daysSelected);
            getParentFragmentManager().setFragmentResult("requestDays", result);

            dismiss();
        });
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

}
