package com.example.infreminder.logic;

import android.util.Log;

import com.example.infreminder.database.ReminderDatabase;
import com.example.infreminder.logic.interfaces.I_CreateAlarmLogic;
import com.example.infreminder.reminder.Reminder;
import com.example.infreminder.view.interfaces.I_CreateAlarmView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class CreateAlarmLogic implements I_CreateAlarmLogic {

    private I_CreateAlarmView createAlarmView;

    public CreateAlarmLogic(I_CreateAlarmView fragment) {
        createAlarmView = fragment;
    }

    @Override
    public void createAlarm(int hour, int min, String name, String desc, ArrayList<Integer> days) {
        Calendar rightNow = Calendar.getInstance();
        int daysToNext = daysToNext(days, rightNow, hour, min);
        // desc es un campo del json¡
        /* GregorianCalendar(year,month,dayofmonth,hourofday,minute) */
        Calendar dateAlarm = new GregorianCalendar(rightNow.get(Calendar.YEAR), rightNow.get(Calendar.MONTH), rightNow.get(Calendar.DAY_OF_MONTH), hour, min);
        dateAlarm.add(Calendar.DAY_OF_MONTH, daysToNext);

        Reminder reminder = new Reminder(name, desc,days.toString(), dateAlarm);

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

    private String dayTranslator(int day){
        String res = "";
        switch(day){
            case Calendar.MONDAY: // 2
                res ="Monday";
                break;
            case Calendar.TUESDAY: // 3
                res = "Tuesday";
                break;
            case Calendar.WEDNESDAY: // 4
                res = "Wednesday";
                break;
            case Calendar.THURSDAY: // 5
                res = "Thursday";
                break;
            case Calendar.FRIDAY: // 6
                res = "Friday";
                break;
            case Calendar.SATURDAY: // 7
                res = "Saturday";
                break;
            case Calendar.SUNDAY: // 1
                res = "Sunday";
                break;
            default:
                res = "";
                break;
        }
        return res;
    }
}
