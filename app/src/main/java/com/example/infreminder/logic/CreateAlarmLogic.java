package com.example.infreminder.logic;

import android.util.Log;

import com.example.infreminder.Utils.Utils;
import com.example.infreminder.database.ReminderDatabase;
import com.example.infreminder.logic.interfaces.I_CreateAlarmLogic;
import com.example.infreminder.pojo.PojoInit;
import com.example.infreminder.pojo.Reminder;
import com.example.infreminder.threads.AlarmManagerThread;
import com.example.infreminder.view.interfaces.I_CreateAlarmView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class CreateAlarmLogic implements I_CreateAlarmLogic {

    private I_CreateAlarmView createAlarmView;

    /**
     * Crea una instancia de la capa lógica.
     *
     * @param fragment
     */
    public CreateAlarmLogic(I_CreateAlarmView fragment) {
        createAlarmView = fragment;
    }

    @Override
    public void createAlarm(int hour, int min, String name, JSONObject jsonObject, ArrayList<String> days) throws JSONException {
        Calendar rightNow = Calendar.getInstance();
        int daysToNext = daysToNext(days, rightNow, hour, min);

        /* GregorianCalendar(year,month,dayofmonth,hourofday,minute) */
        Calendar dateAlarm = new GregorianCalendar(rightNow.get(Calendar.YEAR), rightNow.get(Calendar.MONTH), rightNow.get(Calendar.DAY_OF_MONTH), hour, min,0);
        dateAlarm.add(Calendar.DAY_OF_MONTH, daysToNext);

        /* Añadir campos vacíos */
        jsonObject.put("reply_text", "");
        jsonObject.put("fullscreen", false); // NO FUNCIONA RN
        jsonObject.put("big_desc", false);

        Reminder reminder = PojoInit.reminder(name, Utils.jsonToString(jsonObject), days, dateAlarm);

        new Thread(() -> {
            List<Reminder> listRem = ReminderDatabase.getInstance(createAlarmView.getCreateAlarmView().getContext()).reminderDao().getReminders();

            /* Comprueba que no existen otros recordatorios con ese id */
            boolean everythingOK = false;
            while(!everythingOK){
                boolean exists = false;
                int myid = reminder.getId();
                for(Reminder rem:listRem){
                    if (rem.getId() == myid) {
                        exists = true;
                        break;
                    }
                }
                if(exists){ /* Por cada 10 sonará un minuto después */
                    reminder.setId(myid + 1);
                } else {
                    everythingOK = true;
                }
            }

            /* Crea AlarmManager para gestionar notificaciones*/
            AlarmManagerThread alarmManagerThread = new AlarmManagerThread(createAlarmView.getCreateAlarmView().getContext(),0);
            try {
                alarmManagerThread.throwAlarm(reminder);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            ReminderDatabase.getInstance(createAlarmView.getCreateAlarmView().getContext()).reminderDao().addReminder(reminder);

        }).start();

    }

    @Override
    public void repeatOnlyOnce(int hour, int min, List<String> days) {
        Calendar rightNow = Calendar.getInstance();
        Calendar selectedTime = Calendar.getInstance();
        selectedTime.set(Calendar.HOUR_OF_DAY, hour);
        selectedTime.set(Calendar.MINUTE, min);

        if (rightNow.compareTo(selectedTime) >= 0) {
            days.add((rightNow.get(Calendar.DAY_OF_WEEK) + 1) +"");
        } else {
            days.add(rightNow.get(Calendar.DAY_OF_WEEK) + "");
        }
    }

    @Override
    public void repeatEveryDay(List<String> days) {
        for(int i = 1; i <= 7; i++) days.add(i + "");
    }

    /**
     * Comprueba cuantos días faltan para la siguiente alarma y los devuelve.
     *
     * @param days lista con días de la alarma
     * @param rightNow calendar del momento actual
     * @param hour hora de la alarma
     * @param min minuto de la alarma
     * @return días que faltan para la siguiente alarma
     */
    private int daysToNext(ArrayList<String> days, Calendar rightNow, int hour, int min){
        int today = rightNow.get(Calendar.DAY_OF_WEEK);
        int aux, daysToNext = 9;
        boolean jumpToday = false;

        /* Si la hora es anterior a la actual, faltarían 7 días en caso de que la alarma sonara hoy*/
        if (rightNow.get(Calendar.HOUR_OF_DAY) > hour ||
                (rightNow.get(Calendar.HOUR_OF_DAY) == hour && rightNow.get(Calendar.MINUTE) >= min)) {
            jumpToday = true;
        }
        for(String day:days){
            int int_day = Integer.parseInt(day);
            aux = (int_day - today < 0) ? int_day - today + 7: int_day - today;
            if(aux == 0 && jumpToday) aux = 7; // comprueba si hay que saltar el día de hoy
            if (aux < daysToNext) {
                daysToNext = aux; // daysToNext son los días que falta hasta que suene la alarma
            }
        }
        return daysToNext;
    }

}
