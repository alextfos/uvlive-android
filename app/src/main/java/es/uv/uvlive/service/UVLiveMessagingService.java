package es.uv.uvlive.service;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import es.uv.uvlive.utils.PushUtils;
import es.uv.uvlive.utils.StringUtils;

public class UVLiveMessagingService extends FirebaseMessagingService {
    private static final String TYPE = "type";
    private static final String ID_CONVERSATION = "idConversation";
    private static final String OPERATION = "operation";

    private static final String OPERATION_GET = "GET";
    private static final String OPERATION_MESSAGES = "MESSAGES";
    private static final String OPERATION_CONVERSATIONS = "CONVERSATIONS";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        if (remoteMessage.getNotification() != null) {
            String title = remoteMessage.getNotification().getTitle();
            String text = remoteMessage.getNotification().getBody();
            if (!StringUtils.isBlank(title) && !StringUtils.isBlank(text)) {
                PushUtils.sendNotification(this, title, text);
            }
        }
        String type = "";
        String operation = "";
        int idConversation;

        for (String key: remoteMessage.getData().keySet()) {
            if (TYPE.equals(key)) {
                type = remoteMessage.getData().get(key);
            } else if (ID_CONVERSATION.equals(key)) {
                idConversation = Integer.parseInt(remoteMessage.getData().get(key));
            } else if (OPERATION.equals(key)) {
                operation = remoteMessage.getData().get(key);
            }
        }

        if (OPERATION_GET.equals(type) && OPERATION_MESSAGES.equals(operation)) {
            // TODO request messages
        } else if (OPERATION_GET.equals(type) && OPERATION_CONVERSATIONS.equals(operation)) {
            // TODO request conversations
        }
//            PushUtils.sendNotification(this, "UVLive", "Tieneh usteh mensajes nuevos");
    }
}
