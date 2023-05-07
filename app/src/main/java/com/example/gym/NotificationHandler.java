package com.example.gym;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import androidx.core.app.NotificationCompat;

public class NotificationHandler {
    private static final String CHANNLE_ID = "gym_notification_channel";
    private final int NOTIFICATION_ID = 0;
    private NotificationManager notificationManager;
    private Context context;

    public NotificationHandler(Context context) {
        this.notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        this.context = context;

        createChannel();
    }

    private void createChannel(){
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.O){
            return;
        }

        NotificationChannel channel = new NotificationChannel(CHANNLE_ID, "Gym Notification",NotificationManager.IMPORTANCE_DEFAULT);

        channel.enableVibration(true);
        channel.setDescription("Értesítés a Gym alkalmazásból.");
        this.notificationManager.createNotificationChannel(channel);

    }

    public void send(String message){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNLE_ID)
                .setContentTitle("Gym Alkalmazás")
                .setContentText(message)
                .setSmallIcon(R.drawable.baseline_fitness_center_24);

        this.notificationManager.notify(NOTIFICATION_ID, builder.build());
    }
}
