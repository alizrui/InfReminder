package com.example.infreminder.fragmentsview;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.provider.Settings;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;

import com.example.infreminder.R;
import com.example.infreminder.preference.TimePreference;
import com.example.infreminder.preference.TimePreferenceDialogFragmentCompat;

public class SettingsFragment extends PreferenceFragmentCompat {

    private static final String KEY_RINGTONE_PREFERENCE = "ringtone";
    private static final String KEY_VIBRATION_PREFERENCE = "vibration";
    private int REQUEST_CODE_ALERT_RINGTONE;
    private SharedPreferences prefs;

    /**
     * Carga del xml de Preferences
     * Este método carga la vista de Preferences y inicia un Shared Preferences
     *
     * @param savedInstanceState
     * @param rootKey
     */
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey);
        prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        SharedPreferences.Editor editor = prefs.edit();
    }

    /**
     * Muestra el TimePreference y carga en SharedPrefence la hora elegida con formato hh:mm
     * Utilizado para cargar la hora en la que suenen las notificaciones
     *
     * @param preference
     */
    @Override
    public void onDisplayPreferenceDialog(Preference preference) {
        DialogFragment dialogFragment = null;
        FragmentManager fm = getParentFragmentManager();
        if (preference instanceof TimePreference) {
            dialogFragment = TimePreferenceDialogFragmentCompat.newInstance(preference.getKey());
        }
        if (dialogFragment != null) {
            dialogFragment.setTargetFragment(this, 0);
            dialogFragment.show(fm, "android.support.v14.preference.PreferenceFragment.DIALOG");
        } else {
            super.onDisplayPreferenceDialog(preference);
        }
    }

    /**
     *Realiza una acción dependiendo de la preferencia pulsada por el usuario
     * Si la preferencia seleccionada es Ringtone se abre un RingtonManager
     * En caso contrario el dispositivo vibra si el usuario ha seleccionado la opción de vibración
     *
     * @param preference
     * @return
     */
    @Override
    public boolean onPreferenceTreeClick(Preference preference) {
        if (preference.getKey().equals(KEY_RINGTONE_PREFERENCE)) {
            Intent intent = new Intent(RingtoneManager.ACTION_RINGTONE_PICKER);
            intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TYPE, RingtoneManager.TYPE_ALARM);
            intent.putExtra(RingtoneManager.EXTRA_RINGTONE_SHOW_DEFAULT, true);
            intent.putExtra(RingtoneManager.EXTRA_RINGTONE_SHOW_SILENT, true);
            intent.putExtra(RingtoneManager.EXTRA_RINGTONE_DEFAULT_URI, Settings.System.DEFAULT_NOTIFICATION_URI);

            String existingValue = prefs.getString("ringtone",null);
            if (existingValue != null) {
                if (existingValue.length() == 0) {
                    intent.putExtra(RingtoneManager.EXTRA_RINGTONE_EXISTING_URI, (Uri) null);
                } else {
                    intent.putExtra(RingtoneManager.EXTRA_RINGTONE_EXISTING_URI, Uri.parse(existingValue));
                }
            } else {
                intent.putExtra(RingtoneManager.EXTRA_RINGTONE_EXISTING_URI, Settings.System.DEFAULT_NOTIFICATION_URI);
            }
            startActivityForResult(intent,REQUEST_CODE_ALERT_RINGTONE);
        }
        else if(preference.getKey().equals(KEY_VIBRATION_PREFERENCE)) {
            if(prefs.getString("vibration", getString(R.string.vibrate_no)) != null) {
                Vibrator v = (Vibrator) getActivity().getSystemService(getContext().VIBRATOR_SERVICE);
                v.vibrate(300);
            }
        }
        return super.onPreferenceTreeClick(preference);
    }

    /**
     * Captura la ruta del sonido de alarma seleccionado por el usuario
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_CODE_ALERT_RINGTONE && data != null) {
            Uri ringtone = data.getParcelableExtra(RingtoneManager.EXTRA_RINGTONE_PICKED_URI);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("ringtone",ringtone.toString());
            editor.apply();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}