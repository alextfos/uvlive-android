package es.uv.uvlive.service;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import es.uv.uvlive.utils.PushUtils;

/**
 * Created by alextfos on 19/12/2016.
 */

public class UVLiveMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        PushUtils.sendNotification(this, remoteMessage.getNotification().getTitle(),
                remoteMessage.getNotification().getBody());
        //TODO
    }
}
