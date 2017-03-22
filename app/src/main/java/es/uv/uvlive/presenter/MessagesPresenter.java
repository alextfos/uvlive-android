package es.uv.uvlive.presenter;

import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.List;

import es.uv.uvlive.data.database.models.MessageTable;
import es.uv.uvlive.data.database.models.MessageTable_Table;
import es.uv.uvlive.data.gateway.form.MessagesForm;
import es.uv.uvlive.data.gateway.response.MessageListResponse;
import es.uv.uvlive.session.MessageModel;
import es.uv.uvlive.ui.actions.MessageActions;

/**
 * Created by atraver on 22/03/17.
 */

public class MessagesPresenter extends BasePresenter {

    private MessageActions messageActions;

    public MessagesPresenter(MessageActions messageActions) {
        this.messageActions = messageActions;
    }

    public void getMessages(long idConversation) {
        List<MessageTable> messageList = SQLite.select()
                .from(MessageTable.class)
                .where(MessageTable_Table.idConversation_id.is(idConversation))
                .queryList();
        final List<MessageModel> messageDBList = MessageModel.transform(messageList);
        messageActions.onMessagesReceived(messageDBList);

        Response.Listener<MessageListResponse> responseListener = new Response.Listener<MessageListResponse>() {
            @Override
            public void onResponse(MessageListResponse messageListResponse) {
                List<MessageModel> messages = MessageModel.transform(messageListResponse.getMessageResponse());
                for (MessageModel message: messages) {
                    if (!messageDBList.contains(message)) {
                        MessageTable messageTable = new MessageTable();
                        messageTable.setMessageText(message.getMessage());
                        messageTable.setIdConversation(message.getIdConversation());
                        messageTable.setTimeStamp(message.getTimeStamp());
                        messageTable.setSended(true);
                        messageTable.save();
                    }
                }
                messageActions.onMessagesReceived(messages);
            }

        };
        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.d("proves", "Conversaciones - Error");
            }
        };

        MessagesForm messagesForm = new MessagesForm();
        messagesForm.setIdConversation(idConversation);
//        messagesForm.setLastMessage();

//        UVLiveApplication.getUVLiveGateway().messages(messagesForm,responseListener, errorListener);
    }

    public void sendMessage(long idConversation, String message) {
        MessageTable messageTable = new MessageTable();

        messageTable.setSended(false);
        messageTable.setMessageText(message);
        messageTable.setIdConversation(idConversation);
        messageTable.save();

        // Reload messages list
        getMessages(idConversation);

        //TODO request and update
    }
}
