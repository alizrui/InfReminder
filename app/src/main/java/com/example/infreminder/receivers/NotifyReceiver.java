package com.example.infreminder.receivers;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.app.RemoteInput;
import androidx.core.app.TaskStackBuilder;

import com.example.infreminder.MainActivity;
import com.example.infreminder.R;
import com.example.infreminder.activities.IntroActivity;
import com.example.infreminder.database.ReminderDatabase;
import com.example.infreminder.reminder.Reminder;
import com.example.infreminder.threads.AlarmManagerThread;

public class NotifyReceiver extends BroadcastReceiver {

    private final String CHANNEL_ID = "notify_channel_id";



    @Override
    public void onReceive(Context context, Intent intent) {
        int id = intent.getIntExtra("id", -1);
        if (id == -1) { return; }
        String title = intent.getStringExtra("title");

//        /* ESTO HAY QUE QUITARLO DE AQUÍ */
//        new Thread(() -> {
//            /* Recupera el reminder de la database*/
//            Reminder rem = ReminderDatabase.getInstance(context).reminderDao().getReminder(id);
//            if (rem != null) {
//
//                /* Elimina la alarma de la database */
//                ReminderDatabase.getInstance(context).reminderDao().deleteReminder(rem);
//            } else {
//                Log.d("LOL", "no has borrado nada");
//            }
//        }).start();

        AlarmManagerThread thread = new AlarmManagerThread(null, context, id);
        thread.start();


        createNotificationChannel(context);

        PendingIntent resultPendingIntent = createPendingIntent(context, id);

        Notification notification = createNotification(context, resultPendingIntent, title);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
        notificationManagerCompat.notify(id, notification);
    }

    private Notification createNotification(Context context, PendingIntent pendingIntent, String title) {

        //crea la notificación y se le asocia un intent
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_add_alarm)
                .setContentTitle(title)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        builder.setContentIntent(pendingIntent);

        return builder.build();
    }

    private PendingIntent createPendingIntent(Context context, int id) {

        //define el intent que se ejecutará cuando se haga click sobre la notificación
        //el intent abrirá el main y se añadirá a la pila de activities para mejor experiencia de navegación
        Intent resultIntent = new Intent(context, IntroActivity.class); // Hay que poner la pantalla principal que toque, cambiar si se cambia
        resultIntent.putExtra("notify_id", id);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addNextIntentWithParentStack(resultIntent);
        return stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    private void createNotificationChannel(Context context) {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "getString(R.string.channel_name)";
            String description = "getString(R.string.channel_description)";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }


}
