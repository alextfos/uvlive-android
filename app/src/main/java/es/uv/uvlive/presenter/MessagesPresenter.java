package es.uv.uvlive.presenter;

import android.support.annotation.NonNull;

import com.raizlabs.android.dbflow.sql.language.OrderBy;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.ArrayList;
import java.util.Calendar;
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
import es.uv.uvlive.session.Session;
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

    public void getMessages(boolean endList) {
        this.endList = endList;
        getMessages();
    }

    public void getMessages() {
        if (endList) {
            return;
        }

        List<MessageTable> oldestMessage = SQLite.select()
                .from(MessageTable.class)
                .where(MessageTable_Table.idConversation_id.is(idConversation))
                .and(MessageTable_Table.timestamp.greaterThan(0))
                .orderBy(OrderBy.fromProperty(MessageTable_Table.timestamp).ascending()).limit(1).queryList();

        if (oldestMessage.size() > 0 && oldestMessage.get(0).getTimestamp()>0) {
            long oldestTimestamp = oldestMessage.get(0).getTimestamp();
            MessagesForm messagesForm = new MessagesForm();
            messagesForm.setTimestamp(oldestTimestamp);
            messagesForm.setIdConversation(idConversation);
            UVLiveApplication.getUVLiveGateway().getPreviousMessages(messagesForm, new UVCallback<MessageListResponse>() {
                @Override
                public void onSuccess(@NonNull MessageListResponse messageListResponse) {
                    onMessagesReceived(messageListResponse);
                    if (messageListResponse.getMessages().size() == 0) {
                        endList = true;
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
            message.setMine(isMine(message));
            if (!messageModelList.contains(message)) {
                saveMessage(message);
                messageModelList.add(message);
            } else if (!messageModelList.contains(message) ) {
                int pos = messageModelList.indexOf(message);
                messageModelList.get(pos).setSent(true);
                messageModelList.get(pos).setTimestamp(message.getTimestamp());
                saveMessage(message);
            }
        }

        Collections.sort(messageModelList);
        messageActions.onMessagesReceived(messageModelList);
    }

    private boolean isMine(MessageModel messageModel) {
        return currentUser.getOwnerName().equalsIgnoreCase(messageModel.getOwner());
    }

    private void saveMessage (MessageModel message) {
        MessageTable messageTable = new MessageTable();
        messageTable.setIdMessage(message.getIdMessage());
        messageTable.setMessageText(message.getMessage());
        messageTable.setIdConversation(message.getIdConversation());
        messageTable.setOwner(message.getOwner());
        messageTable.setTimestamp(message.getTimestamp());
        messageTable.setSent(true);
        messageTable.save();
    }

    private void removeMessage(MessageModel message) {
        SQLite.delete().from(MessageTable.class).where(MessageTable_Table.id.eq(message.getIdLocal()));
    }

    public void sendMessage(int idConversation, String message) {
        final MessageTable messageTable = new MessageTable();

        messageTable.setSent(false);
        messageTable.setMessageText(message);
        messageTable.setOwner(currentUser.getOwnerName());
        messageTable.setIdConversation(idConversation);
        messageTable.setTimestamp(Calendar.getInstance().getTimeInMillis());
        messageTable.save();

        MessageForm messageForm = new MessageForm();
        messageForm.setIdConversation(idConversation);
        messageForm.setMessage(message);

        final MessageModel messageModel = new MessageModel(idConversation, messageTable);
        messageModel.setMine(true);
        messageModelList.add(messageModel);
        messageActions.onMessagesReceived(messageModelList);

        UVCallback<BaseResponse> callback = new UVCallback<BaseResponse>() {
            @Override
            public void onSuccess(@NonNull BaseResponse baseResponse) {
                // Reload messages list
                getFollowingMessages();
                messageModel.setSent(true);
                messageTable.setSent(true);
                messageTable.save();
                messageActions.onMessagesReceived(messageModelList);
            }

            @Override
            public void onError(int errorCode) {
                messageActions.onError(errorCode);
                getFollowingMessages();
            }
        };

        UVLiveApplication.getUVLiveGateway().sendMessage(messageForm,callback);
    }

    public void getFollowingMessages() {
        List<MessageTable> newestTimestamp = SQLite.select()
                .from(MessageTable.class)
                .where(MessageTable_Table.idConversation_id.is(idConversation))
                .and(MessageTable_Table.sent.isNot(Boolean.FALSE))
                .and(MessageTable_Table.timestamp.greaterThan(0))

                .orderBy(OrderBy.fromProperty(MessageTable_Table.idMessage).descending()).limit(1).queryList();

        if (!newestTimestamp.isEmpty() && newestTimestamp.get(0).getTimestamp()>0) {
            MessagesForm messagesForm = new MessagesForm();
            messagesForm.setIdConversation(idConversation);
            messagesForm.setTimestamp(newestTimestamp.get(0).getTimestamp());
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

    public void getNewMessages() {
        UVCallback<MessageListResponse> callback = new UVCallback<MessageListResponse>() {

            @Override
            public void onSuccess(@NonNull MessageListResponse messageListResponse) {
                List<MessageModel> result = new ArrayList<>();
                List<MessageModel> messages = MessageModel.transform(idConversation, messageListResponse.getMessages());
                if (messageListResponse.getMessages().size() == 0) {
                    for (MessageModel messageModel: messages) {
                        messageModel.setMine(isMine(messageModel));
                        if (!messageModelList.contains(messageModel)) {
                            result.add(messageModel);
                        }
                    }
                }
                messageActions.onMessagesReceived(result);
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
