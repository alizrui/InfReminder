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

public class NotifyReply extends BroadcastReceiver {

    /**
     * Escucha de un broadcast lanzado a este objeto.
     * El objetivo de esta clase es escuchar el RemoteInput de una notificación. Lo que hace es
     * recuperar la información de la notificación y ver si el texto escrito es igualal que debía
     * escribir y en caso de que lo sea abre la aplicación.
     *
     * @param context
     * @param intent
     */
    @Override
    public void onReceive(Context context, Intent intent) {

        int id = intent.getIntExtra("id", -1);
        String title = intent.getStringExtra("title");
        String replyText = intent.getStringExtra("replyText");

        String text = getMessageText(intent, title + id);

        if (replyText.equals(text)) {
            Log.d("LOL", replyText + "   " + text);

            NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
            notificationManagerCompat.cancel(id);
        }
    }

    /**
     * Recupera el mensaje del RemoteInput.
     * Recordar que la key es la concatenación del título de la notificación con su id.
     *
     * @param intent
     * @param key para recuperar el mensaje
     * @return mensaje escrito en la notificación
     */
    private String getMessageText(Intent intent, String key) {
        Bundle remoteInput = RemoteInput.getResultsFromIntent(intent);
        if (remoteInput != null) {
            return remoteInput.getCharSequence(key).toString();
        }
        return null;
    }
}
