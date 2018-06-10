package es.uv.uvlive.service;

import android.support.annotation.Nullable;

import com.example.atraverf.uvlive.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.ArrayList;
import java.util.List;

import es.uv.uvlive.UVLiveApplication;
import es.uv.uvlive.presenter.ConversationsPresenter;
import es.uv.uvlive.presenter.MessagesPresenter;
import es.uv.uvlive.session.BusinessError;
import es.uv.uvlive.session.Message;
import es.uv.uvlive.ui.actions.BaseActions;
import es.uv.uvlive.ui.actions.ConversationsActions;
import es.uv.uvlive.ui.actions.MessageActions;
import es.uv.uvlive.ui.models.ConversationModel;
import es.uv.uvlive.ui.models.MessageModel;
import es.uv.uvlive.utils.StringUtils;
import es.uv.uvlive.utils.UVNotificationManager;

public class UVLiveMessagingService extends FirebaseMessagingService implements MessageActions, ConversationsActions {
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
            // Application in foreground, the event is managed by the fragment
            if (getCurrentActions() != null && getCurrentActions() instanceof MessageActions &&
                    ((MessageActions) getCurrentActions()).getIdConversation() == idConversation &&
                    !UVLiveApplication.getInstance().isApplicationInForeground()) {
                ((MessageActions)getCurrentActions()).getMessages();
            } else if (idConversation != -1) {
                MessagesPresenter messagesPresenter = new MessagesPresenter(idConversation, this);

                messagesPresenter.getNewMessages();
            }
        // GET CONVERSATIONS
        } else if (OPERATION_GET.equals(type) && OPERATION_CONVERSATIONS.equals(operation)) {
            // Application in foreground, the event is managed by the fragment
            if (getCurrentActions() != null && getCurrentActions() instanceof ConversationsActions &&
                    UVLiveApplication.getInstance().isApplicationInForeground()) {
                ((ConversationsActions)getCurrentActions()).getConversations();
            } else {
                ConversationsPresenter conversationsPresenter = new ConversationsPresenter(this);
                conversationsPresenter.updateConversations();
            }
        }
    }

    private @Nullable BaseActions getCurrentActions() {
        return UVLiveApplication.getBaseActions();
    }

    @Override
    public void onError(BusinessError businessError) {
        // Nothing to do here
    }

    @Override
    public int getIdConversation() {
        return 0; // not used
    }

    @Override
    public void onMessagesReceived(List<MessageModel> messageList) {
        if (messageList != null && !messageList.isEmpty()) {
            List<String> stringList = new ArrayList<>();
            for (MessageModel messageModel : messageList) {
                stringList.add(messageModel.getOwner() + ": " + messageModel.getMessage());
            }
            notificationManager.sendMessageNotification(getString(R.string.notification_messages_title),
                    getString(R.string.notification_messages_bigContentTitle), getString(R.string.notification_messages_text), stringList);
        }
    }

    @Override
    public void getMessages() {
        // Nothing to do here
    }

    @Override
    public void onErrorFetchingMessages(BusinessError errorGettingConversation) {
        onError(errorGettingConversation);
    }

    @Override
    public void onConversationsReceived(List<ConversationModel> conversationModelList) {
        if (conversationModelList != null && !conversationModelList.isEmpty()) {
            ConversationModel conversation = conversationModelList.get(conversationModelList.size()-1);
            if (conversation != null && !StringUtils.isBlank(conversation.getName())) {
                notificationManager.sendPubliNotification(getString(R.string.notification_new_conversation_title, conversation.getName()),
                        getString(R.string.notification_new_conversation_text));
            } else {
                notificationManager.sendPubliNotification(getString(R.string.notification_new_conversation_title_alt),
                        getString(R.string.notification_new_conversation_text));
            }
        }
    }

    @Override
    public void getConversations() {
        // Nothing to here
    }
}
