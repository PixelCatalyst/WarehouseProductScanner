package com.pixcat.warehouseproductscanner.ui.register;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.pixcat.warehouseproductscanner.R;

public class UserCreatedNotification {

    private static final double RANDOM_ID_CONSTANT = 65535.0;
    private static final String CHANNEL_NAME = "UserCreatedNotificationChannel";
    private static final String CHANNEL_DESCRIPTION = "Channel to show notifications about user accounts";
    private static final String CHANNEL_ID = "UserCreatedNotificationChannelId";

    private static volatile NotificationChannel channel;

    private final NotificationManagerCompat notificationManager;
    private final NotificationCompat.Builder builder;

    private UserCreatedNotification(Activity parentActivity, String username) {
        builder = new NotificationCompat.Builder(parentActivity, CHANNEL_ID)
                .setSmallIcon(R.drawable.account_created_icon)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentTitle(parentActivity.getString(R.string.notif_account_created_title))
                .setContentText(parentActivity.getString(R.string.notif_account_created_content, username));
        notificationManager = NotificationManagerCompat.from(parentActivity);
    }

    public void show() {
        notificationManager.notify(randomId(), builder.build());
    }

    private int randomId() {
        return (int) (Math.random() * RANDOM_ID_CONSTANT);
    }

    @SuppressLint("ObsoleteSdkInt")
    public static UserCreatedNotification create(Activity parentActivity, String username) {
        if ((Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) && channel == null) {
            if (channel == null) {
                channel = createChannel(parentActivity);
            }
        }
        return new UserCreatedNotification(parentActivity, username);
    }

    private static NotificationChannel createChannel(Activity parentActivity) {
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, importance);
        channel.setDescription(CHANNEL_DESCRIPTION);
        NotificationManager notificationManager = parentActivity.getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
        return channel;
    }
}
