package es.uv.uvlive.utils;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import com.example.atraverf.uvlive.R;

import java.util.List;

import es.uv.uvlive.ui.activity.MainActivity;
import es.uv.uvlive.ui.activity.SplashActivity;

/**
 * Created by alextfos on 19/12/2016.
 */

public class UVNotificationManager {

    private static final String MESSAGE_NOTIFICATION_GROUP = "es.uv.uvlive.MESSAGE_GROUP";
    private static final int SUMMARY_ID = 0;
    private static int contNotification = 1;
    private static int contPubliNotification = Integer.MAX_VALUE;

    private Context context;
    private NotificationManager notificationManager;

    public UVNotificationManager(Context context) {
        this.context = context;
        notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
    }

    /**
     * Sends a tray notification. Depending on the message, should open the timeline or a detail
     * @param messageBody
     */
    public void sendPubliNotification(String title, String messageBody) {

        NotificationCompat.Builder notificationBuilder = getNotificationBuilder()
                .setGroup(MESSAGE_NOTIFICATION_GROUP)
                .setContentIntent(getLauncherIntent())
                .setContentTitle(title)
                .setContentText(messageBody)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));

        notificationManager.notify(contPubliNotification--, notificationBuilder.build());
    }

    public void sendMessageNotification(String title, String bigContentTitle, String text, List<String> events) {

        NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
        NotificationCompat.Builder builder = getNotificationBuilder()
                .setContentTitle(title)
                .setContentText(text)
                .setGroup(MESSAGE_NOTIFICATION_GROUP)
                .setContentIntent(getLauncherIntent());

        inboxStyle.setBigContentTitle(bigContentTitle);
        for (String event: events) {
            inboxStyle = inboxStyle.addLine(event);
        }

        builder.setStyle(inboxStyle);

        showNotification(builder.build());
    }


    private PendingIntent getLauncherIntent() {
        Intent launcherIntent = new Intent(context, SplashActivity.class);
        launcherIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent intent = PendingIntent.getActivity(context, 0,
                launcherIntent, 0);

        return intent;
    }

    private NotificationCompat.Builder getNotificationBuilder() {
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.mipmap.logo)
                .setAutoCancel(Boolean.TRUE);

        return notificationBuilder;
    }

    private void showNotification(Notification notification) {

        Notification summaryNotification =
                new NotificationCompat.Builder(context)
                        //set content text to support devices running API level < 24
                        .setSmallIcon(R.mipmap.logo)
                        //build summary info into InboxStyle template
                        .setStyle(new NotificationCompat.InboxStyle()
                                .setSummaryText("janedoe@example.com"))
                        //specify which group this notification belongs to
                        .setGroup(MESSAGE_NOTIFICATION_GROUP)
                        //set this notification as the summary for the group
                        .setGroupSummary(true)
                        .build();

        if (notificationManager != null) {
            notificationManager.notify(contNotification++, notification);
            notificationManager.notify(SUMMARY_ID, summaryNotification);
        }
    }
}