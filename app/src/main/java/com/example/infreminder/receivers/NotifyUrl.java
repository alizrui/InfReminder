package com.example.infreminder.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public class NotifyUrl extends BroadcastReceiver {

    /**
     * Escucha de un broadcast lanzado a este objeto.
     * El objetivo de esta clase es abrir el navegador con la página de la wiki que se le pasa por
     * el intent.
     *
     * @param context
     * @param intent
     */
    @Override
    public void onReceive(Context context, Intent intent) {

        String url = intent.getStringExtra("url");

        if (url != null) {
            Intent openUrl = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            openUrl.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(openUrl);
        }
    }
}
