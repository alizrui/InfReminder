package com.example.infreminder.preference;


import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import androidx.annotation.Nullable;
import androidx.preference.DialogPreference;

import com.example.infreminder.R;

/**
 *
 */
public class TimePreference extends DialogPreference {

    private int mTime;
    private int mDialogLayoutResId = R.layout.pref_dialog_time;

    public TimePreference(Context context) {
        this(context, null);
    }

    public TimePreference(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.preferenceStyle);
    }

    public TimePreference(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, defStyleAttr);
    }

    public TimePreference(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        // Du custom stuff here
        // ...
        // read attributes etc.
    }


    public int getTime() {
        return mTime;
    }

    public void setTime(int time) {
        mTime = time;

        // Save to SharedPreference
        persistInt(time);
    }

    @Override
    protected Object onGetDefaultValue(TypedArray a, int index) {
        // El tipo de esta preferencia es Int, por lo que leemos el valor predeterminado de los atributos como Int.
        // El valor de reserva se establece en 0.
        return a.getInt(index, 0);
    }

    @Override
    public int getDialogLayoutResource() {
        return mDialogLayoutResId;
    }

    @Override
    protected void onSetInitialValue(@Nullable Object defaultValue) {
        super.onSetInitialValue(defaultValue);
        //valor inicial de la fecha
        setTime(mTime);
    }

}

