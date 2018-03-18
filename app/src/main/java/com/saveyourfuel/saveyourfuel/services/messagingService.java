package com.saveyourfuel.saveyourfuel.services;

import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.saveyourfuel.saveyourfuel.R;

import java.util.Random;

public class messagingService extends FirebaseMessagingService {
    public messagingService() {
    }
    String TAG = "FMS";
    NotificationManager notificationManager;
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Bitmap tmp = BitmapFactory.decodeResource(getResources(), R.drawable.syflogo);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.syflogo)
                //.setLargeIcon(tmp)
                .setContentTitle(remoteMessage.getData().get("title"))
                .setContentText(remoteMessage.getData().get("message"))
                .setAutoCancel(true)
                .setSound(defaultSoundUri);
        int id = new Random().nextInt(10000);
        notificationManager.notify(id,notificationBuilder.build());

        super.onMessageReceived(remoteMessage);
        Log.d(TAG, "From: " + remoteMessage.getFrom());
       // Log.d(TAG, "Notification Message Body: " + remoteMessage.getNotification().getBody());


    }


}
