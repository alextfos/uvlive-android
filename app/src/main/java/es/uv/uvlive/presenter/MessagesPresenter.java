package es.uv.uvlive.presenter;

import android.support.annotation.NonNull;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.List;

import es.uv.uvlive.UVLiveApplication;
import es.uv.uvlive.data.UVCallback;
import es.uv.uvlive.data.database.models.MessageTable;
import es.uv.uvlive.data.database.models.MessageTable_Table;
import es.uv.uvlive.data.gateway.form.MessageForm;
import es.uv.uvlive.data.gateway.form.MessagesForm;
import es.uv.uvlive.data.gateway.response.BaseResponse;
import es.uv.uvlive.data.gateway.response.MessageListResponse;
import es.uv.uvlive.session.MessageModel;
import es.uv.uvlive.ui.actions.MessageActions;

public class MessagesPresenter extends BasePresenter {

    private MessageActions messageActions;

    public MessagesPresenter(MessageActions messageActions) {
        this.messageActions = messageActions;
    }

    public void getMessages(int idConversation) {
        List<MessageTable> messageList = SQLite.select()
                .from(MessageTable.class)
                .where(MessageTable_Table.idConversation_id.is(idConversation))
                .queryList();
        final List<MessageModel> messageDBList = MessageModel.transform(messageList);
        messageActions.onMessagesReceived(messageDBList);

        UVCallback<MessageListResponse> callback = new UVCallback<MessageListResponse>() {

            @Override
            public void onSuccess(@NonNull MessageListResponse messageListResponse) {
                List<MessageModel> messages = MessageModel.transform(messageListResponse.getMessages());
                for (MessageModel message: messages) {
                    if (!messageDBList.contains(message)) {
                        MessageTable messageTable = new MessageTable();
                        messageTable.setMessageText(message.getMessage());
                        messageTable.setIdConversation(message.getIdConversation());
                        messageTable.setTimeStamp(Integer.parseInt(String.valueOf(message.getTimeStamp())));
                        messageTable.setSended(true);
                        messageTable.save();
                    }
                }
                messageActions.onMessagesReceived(messages);
            }

            @Override
            public void onError(int errorCode) {
                messageActions.onError(errorCode);
            }
        };

        MessagesForm messagesForm = new MessagesForm();
        messagesForm.setIdConversation(idConversation);

        UVLiveApplication.getUVLiveGateway().getMessages(messagesForm,callback);
    }

    public void sendMessage(int idConversation, String message) {
        MessageTable messageTable = new MessageTable();

        messageTable.setSended(false);
        messageTable.setMessageText(message);
        messageTable.setIdConversation(idConversation);
        messageTable.save();

        // Reload messages list
        getMessages(idConversation);

        MessageForm messageForm = new MessageForm();
        messageForm.setIdConversation(idConversation);
        messageForm.setMessage(message);

        UVCallback<BaseResponse> callback = new UVCallback<BaseResponse>() {
            @Override
            public void onSuccess(@NonNull BaseResponse baseResponse) {

            }

            @Override
            public void onError(int errorCode) {
                messageActions.onError(errorCode);
            }
        };

        UVLiveApplication.getUVLiveGateway().sendMessage(messageForm,callback);
    }
}
