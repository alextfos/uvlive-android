package es.uv.uvlive.session;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.raizlabs.android.dbflow.sql.language.OrderBy;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.AsyncModel;
import com.raizlabs.android.dbflow.structure.BaseModel;
import com.raizlabs.android.dbflow.structure.database.transaction.QueryTransaction;

import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import es.uv.uvlive.UVLiveApplication;
import es.uv.uvlive.data.UVCallback;
import es.uv.uvlive.data.database.models.ConversationTable;
import es.uv.uvlive.data.database.models.MessageTable;
import es.uv.uvlive.data.database.models.MessageTable_Table;
import es.uv.uvlive.data.gateway.form.MessagesForm;
import es.uv.uvlive.data.gateway.response.ConversationResponse;
import es.uv.uvlive.data.gateway.response.MessageListResponse;
import es.uv.uvlive.data.gateway.response.MessageResponse;
import es.uv.uvlive.mappers.ConversationMapper;
import es.uv.uvlive.mappers.ErrorMapper;
import es.uv.uvlive.mappers.MessageMapper;
import es.uv.uvlive.utils.CollectionUtils;
import es.uv.uvlive.utils.StringUtils;

public class Conversation {
    private int idConversation;
    private String name;
    private String participant1;
    private String participant2;
    private String ownerName;
    private boolean isEndOfListLoaded;
    private LinkedList<Message> messageList;

    public Conversation(String ownerName, ConversationTable conversationTable) {
        idConversation = conversationTable.getId();
        name = conversationTable.getName();
        participant1 = conversationTable.getParticipant1();
        participant2 = conversationTable.getGetParticipant2();
        isEndOfListLoaded = conversationTable.isEndOfListLoaded();
        this.ownerName = ownerName;
    }

    public Conversation(String ownerName, ConversationResponse conversationResponse) {
        idConversation = conversationResponse.getIdConversation();
        name = conversationResponse.getName();
        participant1 = conversationResponse.getParticipant1();
        participant2 = conversationResponse.getParticipant2();
        this.ownerName = ownerName;

        if (StringUtils.isBlank(name)) {
            if (conversationResponse.getParticipant1() != null && !conversationResponse.getParticipant1().equals(ownerName)) {
                name = conversationResponse.getParticipant1();
            } else {
                name = conversationResponse.getParticipant2();
            }
        }
    }

    public int getIdConversation() {
        return idConversation;
    }

    public void setIdConversation(int idConversation) {
        this.idConversation = idConversation;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParticipant1() {
        return participant1;
    }

    public void setParticipant1(String participant1) {
        this.participant1 = participant1;
    }

    public String getParticipant2() {
        return participant2;
    }

    public void setParticipant2(String participant2) {
        this.participant2 = participant2;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public boolean isEndOfListLoaded() {
        return isEndOfListLoaded;
    }

    public void setEndOfListLoaded(boolean endOfListLoaded) {
        isEndOfListLoaded = endOfListLoaded;
        ConversationMapper.getConversationTableFromConversation(this).async().save();
    }

    public void getLocalMessages(final BusinessCallback<List<Message>> callback) {
        try {
            SQLite.select()
                    .from(MessageTable.class)
                    .where(MessageTable_Table.idConversation_id.is(idConversation))
                    .orderBy(OrderBy.fromProperty(MessageTable_Table.timestamp).descending())
                    .async().queryListResultCallback(new QueryTransaction.QueryResultListCallback<MessageTable>() {
                        @Override
                        public void onListQueryResult(QueryTransaction transaction, @Nullable List<MessageTable> tResult) {
                            Conversation.this.messageList = MessageMapper.getMessageListFromMessageResponseList(idConversation, tResult);
                            callback.onDataReceived(Conversation.this.messageList);
                        }
                    }).execute();
        } catch (Exception e) {
            callback.onError(BusinessError.GENERIC_ERROR);
        }
    }

    public void getPreviousMessages(@Nullable Message oldestMessage, final BusinessCallback<List<Message>> callback) {
        if (oldestMessage != null) {
            UVLiveApplication.getUVLiveGateway().getPreviousMessages(MessageMapper.getMessagesFormFromMessage(oldestMessage), new UVCallback<MessageListResponse>() {
                @Override
                public void onSuccess(@NonNull MessageListResponse messageListResponse) {
                    List<Message> messages = MessageMapper.getMessageListFromMessageResponseList(idConversation, messageListResponse.getMessages());

                    Conversation.this.messageList.addAll(Conversation.this.messageList.size(),messages);
                    callback.onDataReceived(messages);

                    saveMessages(messages);
                }

                @Override
                public void onError(int errorCode) {
                    callback.onError(ErrorMapper.mapError(errorCode));
                }
            });
        } else {
            getMessages(callback);
        }
    }

    public void getFollowingMessages(@Nullable Message newestMessage, final BusinessCallback<List<Message>> callback) {
        if (newestMessage != null) {
            UVLiveApplication.getUVLiveGateway().getFollowingMessages(MessageMapper.getMessagesFormFromMessage(newestMessage),
                new UVCallback<MessageListResponse>() {
                    @Override
                    public void onSuccess(@NonNull MessageListResponse messageListResponse) {
                        List<Message> messages = MessageMapper.getMessageListFromMessageResponseList(idConversation, messageListResponse.getMessages());

                        for (Message message: messages) {
                            if (Conversation.this.messageList.contains(message)) {
                                Conversation.this.messageList.remove(message);
                                message.setSent(true);
                            }
                        }
                        Conversation.this.messageList.addAll(0, messages);
                        callback.onDataReceived(messages);

                        saveMessages(messages);
                    }

                    @Override
                    public void onError(int errorCode) {
                        callback.onError(ErrorMapper.mapError(errorCode));
                    }
                });
        } else {
            getMessages(callback);
        }
    }

    public void getMessages(final BusinessCallback<List<Message>> callback) {
        MessagesForm messagesForm = new MessagesForm();
        messagesForm.setIdConversation(idConversation);

        UVLiveApplication.getUVLiveGateway().getMessages(messagesForm,
                new UVCallback<MessageListResponse>() {

                    @Override
                    public void onSuccess(@NonNull MessageListResponse messageListResponse) {
                        List<Message> messages = MessageMapper.getMessageListFromMessageResponseList(idConversation, messageListResponse.getMessages());

                        Conversation.this.messageList.addAll(messages);
                        callback.onDataReceived(messages);

                        saveMessages(messages);
                    }

                    @Override
                    public void onError(int errorCode) {
                        callback.onError(ErrorMapper.mapError(errorCode));
                    }
                });
    }

    public @Nullable Message getNewestMessage() {
        return !CollectionUtils.isEmpty(messageList)?messageList.get(0):null;
    }

    public @Nullable Message getOldestMessage() {
        return !CollectionUtils.isEmpty(messageList)?messageList.get(messageList.size()-1):null;
    }

    public void saveLocalMessage(String messageText, BusinessCallback<Message> businessCallback) {
        Message message = new Message();

        message.setSent(Boolean.FALSE);
        message.setOwner(getOwnerName());
        message.setIdConversation(idConversation);
        message.setLocalTimestamp(Calendar.getInstance().getTimeInMillis());
        message.setTimestamp(-1); // We don't know it
        message.setMessage(messageText);

        this.messageList.add(0,message);
        saveMessage(message);

        businessCallback.onDataReceived(message);
    }

    public void sendMessage(final Message message, final BusinessCallback<Message> businessCallback) {
        UVLiveApplication.getUVLiveGateway().sendMessage(MessageMapper.getMessageFormFromMessage(message),
                new UVCallback<MessageResponse>() {

            @Override
            public void onSuccess(@NonNull MessageResponse messageResponse) {
                message.setOwner(messageResponse.getOwner());
                message.setSent(true);
                message.setTimestamp(messageResponse.getTimestamp());
                message.setMessage(messageResponse.getText());
                message.setIdMessage(messageResponse.getIdMessage());

                MessageMapper.getMessageTableFromMessage(message).async().save();

                businessCallback.onDataReceived(message);
            }

            @Override
            public void onError(int errorCode) {
                BusinessError businessError = ErrorMapper.mapError(errorCode);
                if (BusinessError.USER_BLOCKED.equals(businessError)) {
                    message.setBlocked(true);
                    MessageMapper.getMessageTableFromMessage(message).async().save();
                    businessCallback.onDataReceived(message);
                    businessCallback.onError(businessError);
                }
            }
        });
    }

    /*
    * Private methods
    * */

    private void saveMessages(List<Message> messageList) {
        for (Message message: messageList) {
            saveMessage(message);
        }
    }

    private void saveMessage(final Message message) {
        MessageMapper.getMessageTableFromMessage(message).async().withListener(new AsyncModel.OnModelChangedListener<BaseModel>() {
            @Override
            public void onModelChanged(BaseModel model) {
                if (model != null && model instanceof MessageTable) {
                    message.setIdLocal(((MessageTable) model).getId());
                }
            }
        }).save();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj) || (obj != null && obj instanceof Conversation && ((Conversation) obj).idConversation == idConversation
                && ((Conversation) obj).name != null && ((Conversation) obj).name.equals(name));
    }
}
