package com.example.infreminder.logic;

import android.util.Log;

import com.example.infreminder.Utils.Features;
import com.example.infreminder.Utils.Utils;
import com.example.infreminder.database.ReminderDatabase;
import com.example.infreminder.logic.interfaces.I_CreateAlarmLogic;
import com.example.infreminder.pojo.PojoInit;
import com.example.infreminder.pojo.Reminder;
import com.example.infreminder.threads.AlarmManagerThread;
import com.example.infreminder.view.interfaces.I_CreateAlarmView;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import static java.lang.Boolean.parseBoolean;

public class CreateAlarmLogic implements I_CreateAlarmLogic {
    private I_CreateAlarmView createAlarmView;
    public CreateAlarmLogic(I_CreateAlarmView fragment) {
        createAlarmView = fragment;
    }

    @Override
    public void createAlarm(int hour, int min, String name, JSONObject jsonObject, ArrayList<Integer> days) throws JSONException {
        Calendar rightNow = Calendar.getInstance();
        int daysToNext = daysToNext(days, rightNow, hour, min);

        /* GregorianCalendar(year,month,dayofmonth,hourofday,minute) */
        Calendar dateAlarm = new GregorianCalendar(rightNow.get(Calendar.YEAR), rightNow.get(Calendar.MONTH), rightNow.get(Calendar.DAY_OF_MONTH), hour, min,0);
        dateAlarm.add(Calendar.DAY_OF_MONTH, daysToNext);

        /* Json con las características*/
        //String json = createJson(features);
        jsonObject.put("reply_text", "hola");
        jsonObject.put("repeat_minute", 1);

        //Reminder reminder = new Reminder(name, desc,days.toString(), dateAlarm);
        ArrayList<String> daysString = new ArrayList<>();
        for(int d:days){
            daysString.add(d+"");
        }

        Reminder reminder = PojoInit.reminder(name, Utils.jsonToString(jsonObject), daysString, dateAlarm);

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
            AlarmManagerThread alarmManagerThread = new AlarmManagerThread(createAlarmView.getCreateAlarmView().requireActivity(),
                    createAlarmView.getCreateAlarmView().getContext(),0);
            alarmManagerThread.throwAlarm(reminder);

            ReminderDatabase.getInstance(createAlarmView.getCreateAlarmView().getContext()).reminderDao().addReminder(reminder);
        }).start();
    }


    /**
     *  Comprueba cuantos días faltan para el siguiente día de alarma y los devuelve
     *  */
    private int daysToNext(ArrayList<Integer> days, Calendar rightNow, int hour, int min){
        int today = rightNow.get(Calendar.DAY_OF_WEEK);
        int aux, daysToNext = 9;
        boolean jumpToday = false;

        /* Si la hora es anterior a la actual, faltarían 7 días en caso de que la alarma sonara hoy*/
        if (rightNow.get(Calendar.HOUR_OF_DAY) > hour ||
                (rightNow.get(Calendar.HOUR_OF_DAY) == hour && rightNow.get(Calendar.MINUTE) >= min)) {
            jumpToday = true;
        }
        for(Integer day:days){
            aux = (day - today < 0) ? day - today + 7: day - today;
            if(aux == 0 && jumpToday) aux = 7; // comprueba si hay que saltar el día de hoy
            if (aux < daysToNext) {
                daysToNext = aux; // daysToNext son los días que falta hasta que suene la alarma
            }
        }
        return daysToNext;
    }

}
