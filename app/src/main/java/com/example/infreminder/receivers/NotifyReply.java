package com.example.infreminder.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.core.app.NotificationManagerCompat;
import androidx.core.app.RemoteInput;
import androidx.media.app.NotificationCompat;

import com.example.infreminder.activities.IntroActivity;
import com.example.infreminder.activities.ReplyActivity;

public class NotifyReply extends BroadcastReceiver {

    /**
     * Escucha de un broadcast lanzado a este objeto.
     * Lanza la aplicación en una pantalla para poder responder la notificación
     *
     * @param context
     * @param intent
     */
    @Override
    public void onReceive(Context context, Intent intent) {

        int id = intent.getIntExtra("id", -1);
        String title = intent.getStringExtra("title");

        Intent closeNotificationPanelIntent = new Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
        context.sendBroadcast(closeNotificationPanelIntent);

        Intent resultIntent = new Intent(context, ReplyActivity.class);
        resultIntent.putExtra("notify_id", id);
        resultIntent.putExtra("title", title);
        resultIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(resultIntent);
    }
}
