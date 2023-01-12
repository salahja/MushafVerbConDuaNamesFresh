package com.example.mushafconsolidated.receivers;


import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import android.view.View;
import android.widget.RemoteViews;

import androidx.core.app.NotificationCompat;

import com.example.mushafconsolidated.R;


public class NotificationChannelManager {


//    NotificationManager.IMPORTANCE_DEFAULT

    private static void createNotificationChannel(Context context, String id, String name, int importance) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel serviceChannel = new NotificationChannel(
                    id,
                    name,
                    importance
            );

            NotificationManager manager = context.getSystemService(NotificationManager.class);
            if (manager == null) return;
            manager.createNotificationChannel(serviceChannel);
        }
    }


    public static NotificationCompat.Builder createSmallMediaNotificcation(
            Context context, String channelId, String channelName
            , RemoteViews content
    ) {


        createNotificationChannel(context, channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT);

        return new NotificationCompat.Builder(context, channelId)
                .setSmallIcon(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP
                        ? R.drawable.ic_quran_trans : R.drawable.logo)
                .setPriority(Notification.PRIORITY_MAX)
                .setContent(content)
                .setOngoing(true);
    }

}
