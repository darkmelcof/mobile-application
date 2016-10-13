package com.darkmelcof.menus;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by Darkmelcof on 19/12/2015.
 */

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "On y est ! ", Toast.LENGTH_LONG).show();

        //R�cup�ration du notification Manager
        NotificationManager notificationManager = (NotificationManager)context.getSystemService(context.NOTIFICATION_SERVICE);

        //Cr�ation de la notification avec sp�cification de l'ic�ne de la notification et le texte qui apparait � la cr�ation de la notification
        Notification notification = new Notification(R.drawable.ic_media_play, "Course terminee", System.currentTimeMillis());

        //D�finition de la redirection au moment du clic sur la notification. Dans notre cas la notification redirige vers notre application
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, new Intent(context, MainActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);

        //On d�finit le titre de la notif
        String titreNotification = "Course terminee";
        //On d�finit le texte qui caract�rise la notif
        String texteNotification = "Recommences :)";

        //Notification & Vibration
        notification.setLatestEventInfo(context, titreNotification, texteNotification, pendingIntent);
        notification.vibrate = new long[] {0,200,100,200,100,200};

        notificationManager.notify(2000, notification);
    }

}

