package com.example.application.baddate;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;


public class AlertReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String incomingContact = intent.getStringExtra("contact");
        String incomingMessage = intent.getStringExtra("message");
        NotificationHelper notificationHelper = new NotificationHelper(context);
        notificationHelper.setContact(incomingContact);
        notificationHelper.setMessage(incomingMessage);
        NotificationCompat.Builder nb = notificationHelper.getChannelNotification();
        notificationHelper.getManager().notify(1, nb.build());
    }
}