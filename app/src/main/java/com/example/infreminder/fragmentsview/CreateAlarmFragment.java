package com.example.infreminder.fragmentsview;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.infreminder.R;
import com.example.infreminder.fragmentsview.interfaces.I_CreateAlarmFragment;

public class CreateAlarmFragment extends Fragment implements I_CreateAlarmFragment {

    public CreateAlarmFragment() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_alarm, container, false);

        final TextView tvAux = view.findViewById(R.id.tvPrueba);

        return view;
    }
}
