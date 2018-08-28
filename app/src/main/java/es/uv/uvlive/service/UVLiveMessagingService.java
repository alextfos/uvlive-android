package es.uv.uvlive.service;

import android.content.Intent;
import android.content.ServiceConnection;
import android.support.annotation.Nullable;

import com.example.atraverf.uvlive.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.ArrayList;
import java.util.List;

import es.uv.uvlive.UVLiveApplication;
import es.uv.uvlive.presenter.BasePresenter;
import es.uv.uvlive.session.BusinessError;
import es.uv.uvlive.ui.actions.BaseActions;
import es.uv.uvlive.ui.actions.ConversationsActions;
import es.uv.uvlive.ui.actions.MessageActions;
import es.uv.uvlive.ui.models.ConversationModel;
import es.uv.uvlive.ui.models.MessageModel;
import es.uv.uvlive.utils.StringUtils;
import es.uv.uvlive.utils.UVNotificationManager;

public class UVLiveMessagingService extends FirebaseMessagingService {
    private static final String TYPE = "type";
    private static final String ID_CONVERSATION = "idConversation";
    private static final String OPERATION = "operation";

    private static final String OPERATION_GET = "GET";
    private static final String OPERATION_MESSAGES = "MESSAGES";
    private static final String OPERATION_CONVERSATIONS = "CONVERSATIONS";

    private UVNotificationManager notificationManager;

    @Override
    public void onCreate() {
        super.onCreate();
        notificationManager = new UVNotificationManager(getApplicationContext());
    }

    @Override
    public boolean bindService(Intent service, ServiceConnection conn, int flags) {
        return super.bindService(service, conn, flags);
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        if (remoteMessage.getNotification() != null) {
            String title = remoteMessage.getNotification().getTitle();
            String text = remoteMessage.getNotification().getBody();
            if (!StringUtils.isBlank(title) && !StringUtils.isBlank(text)) {
                notificationManager.sendPubliNotification(title, text);
            }
        }
        String type = "";
        String operation = "";
        int idConversation = -1;

        for (String key: remoteMessage.getData().keySet()) {
            if (TYPE.equals(key)) {
                type = remoteMessage.getData().get(key);
            } else if (ID_CONVERSATION.equals(key)) {
                idConversation = Integer.parseInt(remoteMessage.getData().get(key));
            } else if (OPERATION.equals(key)) {
                operation = remoteMessage.getData().get(key);
            }
        }

        // GET MESSAGES
        if (OPERATION_GET.equals(type) && OPERATION_MESSAGES.equals(operation)) {
//            LocalBroadcastManager.getInstance(this).sendBroadcastSync();
            // Application in foreground, the event is managed by the fragment
            if (idConversation != -1 && !BasePresenter.notifyMessagesReceived(idConversation)) {
                // TODO make request and store it in database
            }

            if (!UVLiveApplication.getInstance().isApplicationInForeground()) {
                notificationManager.sendNewMessagesNotification(idConversation, getString(R.string.notification_messages_title),
                        getString(R.string.notification_messages_bigContentTitle));
            }

        // GET CONVERSATIONS
        } else if (OPERATION_GET.equals(type) && OPERATION_CONVERSATIONS.equals(operation)) {
            // Application in foreground, the event is managed by the fragment
            if (!BasePresenter.notifyConversationListReceived()) {
                // TODO make request and store it in database
            }

            if (!UVLiveApplication.getInstance().isApplicationInForeground()) {
                notificationManager.sendNewConversationNotification(getString(R.string.notification_new_conversation_title_alt),
                        getString(R.string.notification_new_conversation_text));
            }
        }
    }
}
