package es.uv.uvlive.utils;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

import com.example.atraverf.uvlive.R;

import es.uv.uvlive.data.UVLivePreferences;
import es.uv.uvlive.ui.activity.SplashActivity;

/**
 * Created by alextfos on 19/12/2016.
 */

public class UVNotificationManager extends ContextWrapper {

    private static final String MESSAGE_NOTIFICATION_GROUP = "es.uv.uvlive.MESSAGE_GROUP";

    private static final String PUBLI_CHANNEL = "es.uv.uvlive.PUBLI_CHANNEL";
    private static final String CONVERSATIONS_CHANNEL = "es.uv.uvlive.CONVERSATIONS_CHANNEL";
    private static final String MESSAGES_CHANNEL = "es.uv.uvlive.MESSAGES_CHANNEL";

    private static final int CONVERSATIONS_ID = 0;
    private static final int MESSAGES_ID = 1;

    private static int contPubliNotification = Integer.MAX_VALUE;

    private NotificationManager notificationManager;

    public UVNotificationManager(Context context) {
        super(context);
        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // We create the notification channels
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel publiChannel = new NotificationChannel(PUBLI_CHANNEL,
                    getString(R.string.notification_publi_channel), NotificationManager.IMPORTANCE_MIN);
            publiChannel.setLightColor(Color.GREEN);
            publiChannel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
            getManager().createNotificationChannel(publiChannel);

            NotificationChannel conversationChannel = new NotificationChannel(CONVERSATIONS_CHANNEL,
                    getString(R.string.notification_conversations_channel), NotificationManager.IMPORTANCE_LOW);
            conversationChannel.setLightColor(Color.BLUE);
            conversationChannel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
            getManager().createNotificationChannel(conversationChannel);

            NotificationChannel messagesChannel = new NotificationChannel(MESSAGES_CHANNEL,
                    getString(R.string.notification_messages_channel), NotificationManager.IMPORTANCE_DEFAULT);
            messagesChannel.setLightColor(Color.RED);
            messagesChannel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
            getManager().createNotificationChannel(messagesChannel);

        }
    }

    /**
     * Sends a tray notification. Depending on the message, should open the timeline or a detail
     * @param messageBody
     */
    public void sendPubliNotification(String title, String messageBody) {

        NotificationCompat.Builder notificationBuilder = getNotificationBuilder(PUBLI_CHANNEL)
                .setSmallIcon(R.mipmap.logo)
//                .setGroup(MESSAGE_NOTIFICATION_GROUP)
                .setContentIntent(getLauncherIntent())
                .setContentTitle(title)
                .setContentText(messageBody)
                .setContentIntent(getLauncherIntent())
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));

        notify(contPubliNotification--, notificationBuilder);
    }

    public void sendNewConversationNotification(String title, String messageBody) {
        NotificationCompat.Builder notificationBuilder = getNotificationBuilder(PUBLI_CHANNEL)
                .setSmallIcon(R.mipmap.logo)
//                .setGroup(MESSAGE_NOTIFICATION_GROUP)
                .setContentIntent(getLauncherIntent())
                .setContentTitle(title)
                .setContentText(messageBody)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));

        notify(CONVERSATIONS_ID, notificationBuilder);
    }

    public void sendNewMessagesNotification(int idConversation, String title, String messageBody) {

        UVLivePreferences.getInstance().saveDeeplinkParams(idConversation);

        NotificationCompat.Builder notificationBuilder = getNotificationBuilder(PUBLI_CHANNEL)
                .setSmallIcon(R.mipmap.logo)
//                .setGroup(MESSAGE_NOTIFICATION_GROUP)
                .setContentIntent(getLauncherIntent())
                .setContentTitle(title)
                .setContentText(messageBody)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));

        notify(MESSAGES_ID, notificationBuilder);
    }

    private PendingIntent getLauncherIntent() {
        Intent launcherIntent = new Intent(this, SplashActivity.class);

        launcherIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK |Intent.FLAG_ACTIVITY_CLEAR_TASK);

        return PendingIntent.getActivity(this, 0,
                launcherIntent, 0);
    }

    private NotificationCompat.Builder getNotificationBuilder(String channelId) {
        return new NotificationCompat.Builder(this, channelId)
                .setSmallIcon(R.mipmap.logo)
                .setAutoCancel(Boolean.TRUE);
    }

    /**
     * Send a notification.
     *
     * @param id The ID of the notification
     * @param notification The notification object
     */
    public void notify(int id, NotificationCompat.Builder notification) {
        getManager().notify(id, notification.build());
    }

    /**
     * Get the notification manager.
     *
     * Utility method as this helper works with it a lot.
     *
     * @return The system service NotificationManager
     */
    private NotificationManager getManager() {
        if (notificationManager == null) {
            notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return notificationManager;
    }
}