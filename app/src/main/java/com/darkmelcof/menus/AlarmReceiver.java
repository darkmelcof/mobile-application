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

        //Récupération du notification Manager
        NotificationManager notificationManager = (NotificationManager)context.getSystemService(context.NOTIFICATION_SERVICE);

        //Création de la notification avec spécification de l'icône de la notification et le texte qui apparait à la création de la notification
        Notification notification = new Notification(R.drawable.ic_media_play, "Course terminee", System.currentTimeMillis());

        //Définition de la redirection au moment du clic sur la notification. Dans notre cas la notification redirige vers notre application
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, new Intent(context, MainActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);

        //On définit le titre de la notif
        String titreNotification = "Course terminee";
        //On définit le texte qui caractérise la notif
        String texteNotification = "Recommences :)";

        //Notification & Vibration
        notification.setLatestEventInfo(context, titreNotification, texteNotification, pendingIntent);
        notification.vibrate = new long[] {0,200,100,200,100,200};

        notificationManager.notify(2000, notification);
    }

}

