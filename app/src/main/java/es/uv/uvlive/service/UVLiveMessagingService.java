package es.uv.uvlive.service;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import es.uv.uvlive.utils.PushUtils;

public class UVLiveMessagingService extends FirebaseMessagingService {
    private static final String TITLE = "GET";
    private static final String MESSAGES = "MESSAGES";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        if (TITLE.equals(remoteMessage.getNotification().getTitle()) &&
                MESSAGES.equals(remoteMessage.getNotification().getBody())) {
            PushUtils.sendNotification(this, "UVLive", "Tieneh usteh mensajes nuevos");
        }
    }
}
