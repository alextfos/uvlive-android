package es.uv.uvlive.presenter;

import android.support.annotation.NonNull;

import com.raizlabs.android.dbflow.sql.language.OrderBy;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.Collections;
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

    private int idConversation;

    private List<MessageModel> messageModelList;
    private boolean endList = false;

    public MessagesPresenter(int idConversation, MessageActions messageActions) {
        this.idConversation = idConversation;
        this.messageActions = messageActions;
        List<MessageTable> messageList = SQLite.select()
                .from(MessageTable.class)
                .where(MessageTable_Table.idConversation_id.is(idConversation))
                .queryList();
        messageModelList = MessageModel.transform(idConversation,messageList);
        Collections.sort(messageModelList);
        messageActions.onMessagesReceived(messageModelList);
    }

    public void getMessages() {
        if (endList) {
            return;
        }

        List<MessageTable> oldestMessage = SQLite.select()
                .from(MessageTable.class)
                .where(MessageTable_Table.idConversation_id.is(idConversation))
                .and(MessageTable_Table.timeStamp.greaterThan(0))
                .orderBy(OrderBy.fromProperty(MessageTable_Table.idMessage).ascending()).limit(1).queryList();

        if (oldestMessage.size() > 0 && oldestMessage.get(0).getTimeStamp()>0) {
            int oldestTimestamp = oldestMessage.get(0).getTimeStamp();
            MessagesForm messagesForm = new MessagesForm();
            messagesForm.setTimestamp(oldestTimestamp);
            messagesForm.setIdConversation(idConversation);
            UVLiveApplication.getUVLiveGateway().getPreviousMessages(messagesForm, new UVCallback<MessageListResponse>() {
                @Override
                public void onSuccess(@NonNull MessageListResponse messageListResponse) {
                    onMessagesReceived(messageListResponse);
                    if (messageListResponse.getMessages().size() == 0) {
                        endList = true;
                        return;
                    }
                }

                @Override
                public void onError(int errorCode) {
                    messageActions.onError(errorCode);
                }
            });
        } else {
            UVCallback<MessageListResponse> callback = new UVCallback<MessageListResponse>() {

                @Override
                public void onSuccess(@NonNull MessageListResponse messageListResponse) {
                    if (messageListResponse.getMessages().size() == 0) {
                        endList = true;
                        return;
                    }
                    onMessagesReceived(messageListResponse);
                }

                @Override
                public void onError(int errorCode) {
                    messageActions.onError(errorCode);
                }
            };

            MessagesForm messagesForm = new MessagesForm();
            messagesForm.setIdConversation(idConversation);

            UVLiveApplication.getUVLiveGateway().getMessages(messagesForm, callback);
        }
    }

    private void onMessagesReceived(MessageListResponse messageListResponse) {
        List<MessageModel> messages = MessageModel.transform(idConversation, messageListResponse.getMessages());
        for (MessageModel message : messages) {
            for (MessageModel localMessage: messageModelList) {
                if (!localMessage.equals(message)) {
                    saveMessage(message);
                    messageModelList.add(message);
                } else if (localMessage.equals(message) && !localMessage.isSended()) {
                    messageModelList.remove(localMessage);
                    saveMessage(localMessage);
                    removeMessage(localMessage);
                    messageModelList.add(message);
                }
            }
        }

        Collections.sort(messageModelList);
        messageActions.onMessagesReceived(messageModelList);
    }

    private void saveMessage (MessageModel message) {
        MessageTable messageTable = new MessageTable();
        messageTable.setIdMessage(message.getIdMessage());
        messageTable.setMessageText(message.getMessage());
        messageTable.setIdConversation(message.getIdConversation());
        messageTable.setOwner(message.getOwner());
        messageTable.setTimeStamp(Integer.parseInt(String.valueOf(message.getTimeStamp())));
        messageTable.setSended(true);
        messageTable.save();
    }

    private void removeMessage(MessageModel message) {
        SQLite.delete().from(MessageTable.class).where(MessageTable_Table.id.eq(message.getIdLocal()));
    }

    public void sendMessage(int idConversation, String message) {
        MessageTable messageTable = new MessageTable();

        messageTable.setSended(false);
        messageTable.setMessageText(message);
        messageTable.setIdConversation(idConversation);
        messageTable.save();

        MessageForm messageForm = new MessageForm();
        messageForm.setIdConversation(idConversation);
        messageForm.setMessage(message);

        UVCallback<BaseResponse> callback = new UVCallback<BaseResponse>() {
            @Override
            public void onSuccess(@NonNull BaseResponse baseResponse) {
                // Reload messages list
                getFollowingMessages();
            }

            @Override
            public void onError(int errorCode) {
                messageActions.onError(errorCode);
                getFollowingMessages();
            }
        };

        UVLiveApplication.getUVLiveGateway().sendMessage(messageForm,callback);
    }

    private void getFollowingMessages() {
        List<MessageTable> newestTimestamp = SQLite.select()
                .from(MessageTable.class)
                .where(MessageTable_Table.idConversation_id.is(idConversation))
                .and(MessageTable_Table.timeStamp.greaterThan(0))
                .orderBy(OrderBy.fromProperty(MessageTable_Table.idMessage).descending()).limit(1).queryList();

        if (newestTimestamp.get(0).getTimeStamp()>0) {
            MessagesForm messagesForm = new MessagesForm();
            messagesForm.setIdConversation(idConversation);
            messagesForm.setTimestamp(newestTimestamp.get(0).getTimeStamp());
            UVLiveApplication.getUVLiveGateway().getFollowingMessages(messagesForm, new UVCallback<MessageListResponse>() {
                @Override
                public void onSuccess(@NonNull MessageListResponse messageListResponse) {
                    onMessagesReceived(messageListResponse);
                }

                @Override
                public void onError(int errorCode) {
                    messageActions.onError(errorCode);
                }
            });
        }
    }
}
