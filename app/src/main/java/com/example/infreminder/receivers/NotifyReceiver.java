package com.example.infreminder.receivers;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.app.RemoteInput;
import androidx.core.app.TaskStackBuilder;

import com.example.infreminder.R;
import com.example.infreminder.activities.IntroActivity;
import com.example.infreminder.pojo.PojoInit;
import com.example.infreminder.pojo.Wiki;
import com.example.infreminder.threads.AlarmManagerThread;

public class NotifyReceiver extends BroadcastReceiver {

    private final String CHANNEL_ID = "notify_channel_id";

    @Override
    public void onReceive(Context context, Intent intent) {

        int id = intent.getIntExtra("id", -1);
        if (id == -1) { return; }
        String name = intent.getStringExtra("name");
        String desc = intent.getStringExtra("desc");
        boolean big_desc = intent.getBooleanExtra("big_desc", false);
        int repeatEvery = intent.getIntExtra("repeatEvery", 0);

        /* Gestión de notificaciones con wiki */
        String replyText = intent.getStringExtra("replyText");
        Wiki wiki = null;

        wiki = PojoInit.wiki(replyText);

        createNotificationChannel(context);

        NotificationCompat.Builder builder = initNotificationBuilder(context, name, desc, wiki, big_desc);

        NotificationCompat.Action replyAction = null;

        Intent resultIntent;

        if (wiki != null) {
            replyAction = createReplyPendingIntent(context, id, name, wiki.getTitle());
            resultIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(wiki.getHref()));
        }
        else {
            resultIntent = new Intent(context, IntroActivity.class).putExtra("notify_id", id);
        }
        PendingIntent resultPendingIntent = createResultPendingIntent(context, resultIntent);

        Notification notification = createNotification(context, builder, resultPendingIntent, replyAction, wiki, repeatEvery);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);

        notificationManagerCompat.notify(id, notification);

        AlarmManagerThread thread = new AlarmManagerThread(context, id);
        thread.start();

    }




    /**
     * Crea el objeto de la notificación.
     * Se le asigna un intent cuando se pulse la notificación. En caso que sea una notificación con
     * respuesta se le enlaza el botón con la acción de respuesta y con el botón para abrir la wiki.
     * Además se comprueba si la notificación se debe quedar en el canal.
     *
     * @param context
     * @param builder la notificación en construcción
     * @param pendingIntent acción al pulsar la notificación
     * @param replyAction acción si se debe responder
     * @param wiki en caso de que se tenga que responder
     * @param repeatEvery si es -1 la notificación se quedará en el canal
     * @return el objeto Notification
     */

    private Notification createNotification(Context context, @NonNull NotificationCompat.Builder builder,
                                            @NonNull PendingIntent pendingIntent, NotificationCompat.Action replyAction, Wiki wiki, int repeatEvery) {
        builder.setContentIntent(pendingIntent);

        if (replyAction != null) {
            builder.addAction(replyAction);
            Intent openUrlAction = new Intent(context, NotifyUrl.class).putExtra("url", wiki.getHref());
            PendingIntent openUrlActionPendingIntent = PendingIntent.getBroadcast(context, 0, openUrlAction, 0);
            builder.addAction(R.drawable.ic_alarm, context.getString(R.string.open_wiki), openUrlActionPendingIntent);
        }

        Notification notification = builder.build();

        if (repeatEvery == -1) {
            notification.flags |= Notification.FLAG_NO_CLEAR;
        }

        return notification;
    }

    /**
     * Declara una notificación en construcción.
     * Se le comunica a la notificación en qué canal será mostrado y unos parámetros genéricos.
     *
     * @param context
     * @param title de la notificación
     * @param desc de la notificación
     * @param wiki con la información para, en caso que sea una notificación con respuesta, se
     *             la respuesta que debe tener
     * @return la notificación en construccion
     */

    private NotificationCompat.Builder initNotificationBuilder(@NonNull Context context, @NonNull String title, String desc, Wiki wiki, boolean big_desc) {

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_alarm)
                .setContentTitle(title)
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        if(big_desc){
            builder.setStyle(new NotificationCompat.BigTextStyle().bigText(desc));
        }
        else if (desc != null && !desc.isEmpty()) {
            builder.setContentText(desc);
        }

        if (wiki != null) {
            String largeText = wiki.getTitle() + "\n" + wiki.getText();
            builder.setStyle(new NotificationCompat.BigTextStyle().bigText(largeText));
        }

        return builder;
    }

    /**
     * Declara la acción hace la notificación.
     * Se construye un RemoteInput para poder responder la notificación usando de key la concatenación
     * del título de la notificación y el id. Luego define un intent que llamará a NotifyReply donde
     * le definiremos unos extras. Luego con ese intent creamos el PendingIntent que hará el broadcast
     * para que pueda escucharlo NotifyReply. Finalmente creamos la acción que lanzará el broadcast
     * para poder enlazarla a la notificación.
     *
     * @param context
     * @param id de la notificación
     * @param title de la notificación
     * @param replyText texto que debe reescribirse
     * @return action con el input y la acción que hará al enviar ese input
     */

    private NotificationCompat.Action createReplyPendingIntent(Context context, int id, String title, String replyText) {

        String keyReply = title + id;
        RemoteInput remoteInput = new RemoteInput.Builder(keyReply).setLabel(replyText).build();

        Intent intent = new Intent(context, NotifyReply.class);
        intent.putExtra("id", id);
        intent.putExtra("title", title);
        intent.putExtra("replyText", replyText);

        PendingIntent replyPendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        return new NotificationCompat.Action.Builder(R.drawable.ic_alarm, context.getString(R.string.reply), replyPendingIntent)
                .addRemoteInput(remoteInput)
                .build();
    }

    /**
     * Define para el intent que se lanzará que debe se apilardo en el stack de activities cuando
     * salte en la pantalla.
     * El intent queda a la espera de ser lanzado y cuando esto ocurra se apilará en las activities
     * para mejor experiencia de navegación
     *
     * @param context
     * @param intent que se debe se ejecutará y se apilará
     * @return el intent que queda a la espera de ser lanzado
     */
    private PendingIntent createResultPendingIntent(Context context, Intent intent) {

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addNextIntentWithParentStack(intent);
        return  stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    /**
     * Registra un canal de notificaciones en el sistema para la aplicación.
     *
     * @param context
     */
    private void createNotificationChannel(Context context) {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = context.getString(R.string.notify_for_reminder);
            String description = context.getString(R.string.notify_for_reminder_summary);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            channel.enableVibration(true); // mirar como hacer la vibración @alejandro
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }


}
