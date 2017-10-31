package es.uv.uvlive.service;

import android.support.annotation.Nullable;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.ArrayList;
import java.util.List;

import es.uv.uvlive.UVLiveApplication;
import es.uv.uvlive.presenter.ConversationsPresenter;
import es.uv.uvlive.presenter.MessagesPresenter;
import es.uv.uvlive.session.ConversationModel;
import es.uv.uvlive.session.MessageModel;
import es.uv.uvlive.ui.actions.BaseActions;
import es.uv.uvlive.ui.actions.ConversationsActions;
import es.uv.uvlive.ui.actions.MessageActions;
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

        if (OPERATION_GET.equals(type) && OPERATION_MESSAGES.equals(operation)) {
            if (getCurrentActions() != null && getCurrentActions() instanceof MessageActions) {
                ((MessageActions)getCurrentActions()).getMessages();
            } else if (idConversation != -1) {
                MessagesPresenter messagesPresenter = new MessagesPresenter(idConversation, new MessageActions() {
                    @Override
                    public void onMessagesReceived(List<MessageModel> messageModelList) {
                        List<String> stringList = new ArrayList<>();
                        for (MessageModel messageModel: messageModelList) {
                            stringList.add(messageModel.getOwner() + ": " + messageModel.getMessage());
                        }
                        PushUtils.sendNotification(UVLiveMessagingService.this.getApplicationContext(),
                                "UVLiveModel", "Mensajes nuevos", "Mensajes nuevos", stringList);
                    }

                    @Override
                    public void getMessages() {

                    }

                    @Override
                    public void onError(int errorCode) {

                    }
                });

                messagesPresenter.getNewMessages();
            }
        } else if (OPERATION_GET.equals(type) && OPERATION_CONVERSATIONS.equals(operation)) {
            if (getCurrentActions() != null && getCurrentActions() instanceof ConversationsActions) {
                ((ConversationsActions)getCurrentActions()).getConversations();
            } else {
                ConversationsPresenter conversationsPresenter = new ConversationsPresenter(new ConversationsActions() {
                    @Override
                    public void onConversationsReceived(List<ConversationModel> conversationModelList) {
                        Log.d("messaging", "Hi");
                    }

                    @Override
                    public void getConversations() {

                    }

                    @Override
                    public void onError(int errorCode) {

                    }
                });

                conversationsPresenter.getConversations();
            }
        }
//            PushUtils.sendNotification(this, "UVLive", "Tieneh usteh mensajes nuevos");
    }

    private @Nullable BaseActions getCurrentActions() {
        return UVLiveApplication.getBaseActions();
    }
}
