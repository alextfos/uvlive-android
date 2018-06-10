package es.uv.uvlive.session;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import es.uv.uvlive.UVLiveApplication;
import es.uv.uvlive.data.UVCallback;
import es.uv.uvlive.data.database.models.ConversationTable;
import es.uv.uvlive.data.database.models.MessageTable;
import es.uv.uvlive.data.database.models.MessageTable_Table;
import es.uv.uvlive.data.gateway.form.MessagesForm;
import es.uv.uvlive.data.gateway.response.BaseResponse;
import es.uv.uvlive.data.gateway.response.ConversationResponse;
import es.uv.uvlive.data.gateway.response.MessageListResponse;
import es.uv.uvlive.mappers.ErrorMapper;
import es.uv.uvlive.mappers.MessageMapper;
import es.uv.uvlive.utils.CollectionUtils;
import es.uv.uvlive.utils.StringUtils;

public class Conversation {
    private int id;
    private String name;
    private String participant1;
    private String participant2;
    private String ownerName;
    private List<Message> messageList;

    public Conversation(String ownerName, ConversationTable conversationTable) {
        id = conversationTable.getId();
        name = conversationTable.getName();
        participant1 = conversationTable.getParticipant1();
        participant2 = conversationTable.getGetParticipant2();
        this.ownerName = ownerName;
    }

    public Conversation(String ownerName, ConversationResponse conversationResponse) {
        id = conversationResponse.getId();
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    @Override
    public boolean equals(Object obj) {
        return obj != null && obj instanceof Conversation && ((Conversation) obj).id == id
                && ((Conversation) obj).name != null && ((Conversation) obj).name.equals(name);
    }

    public void getLocalMessages(BusinessCallback<List<Message>> callback) {
        List<MessageTable> messageList = SQLite.select()
                .from(MessageTable.class)
                .where(MessageTable_Table.idConversation_id.is(id))
                .queryList();
        this.messageList = MessageMapper.getMessageListFromMessageResponseList(id,messageList);
        Collections.sort(this.messageList);

        callback.onDataReceived(this.messageList);
    }

    public void getMessages(@Nullable Message oldestMessage, final BusinessCallback<List<Message>> callback) {

        if (oldestMessage != null) {
            UVLiveApplication.getUVLiveGateway().getPreviousMessages(MessageMapper.getMessagesFormFromMessage(oldestMessage), new UVCallback<MessageListResponse>() {
                @Override
                public void onSuccess(@NonNull MessageListResponse messageListResponse) {
                    callback.onDataReceived(parseMessages(messageListResponse));
                }

                @Override
                public void onError(int errorCode) {
                    callback.onError(ErrorMapper.mapError(errorCode));
                }
            });
        } else {
            MessagesForm messagesForm = new MessagesForm();
            messagesForm.setIdConversation(id);

            UVLiveApplication.getUVLiveGateway().getMessages(messagesForm,
                    new UVCallback<MessageListResponse>() {

                @Override
                public void onSuccess(@NonNull MessageListResponse messageListResponse) {

                    callback.onDataReceived(parseMessages(messageListResponse));
                }

                @Override
                public void onError(int errorCode) {
                    callback.onError(ErrorMapper.mapError(errorCode));
                }
            });
        }
    }

    public void getNewMessages(final BusinessCallback<List<Message>> callback) {
        MessagesForm messagesForm = new MessagesForm();
        messagesForm.setIdConversation(id);

        UVLiveApplication.getUVLiveGateway().getMessages(messagesForm,
                new UVCallback<MessageListResponse>() {

            @Override
            public void onSuccess(@NonNull MessageListResponse messageListResponse) {
                List<Message> result = new ArrayList<>();
                List<Message> messages = MessageMapper.getMessageListFromMessageResponseList(id, messageListResponse.getMessages());
                if (messageListResponse.getMessages().size() == 0) {
                    for (Message messageModel: messages) {
                        if (!messageList.contains(messageModel)) {
                            result.add(messageModel);
                        }
                    }
                }
                callback.onDataReceived(result);
            }

            @Override
            public void onError(int errorCode) {
                callback.onError(ErrorMapper.mapError(errorCode));
            }
        });
    }

    public @Nullable Message getNewestMessage() {
        /* Last implementation
        List<MessageTable> newestTimestamp = SQLite.select()
                .from(MessageTable.class)
                .where(MessageTable_Table.idConversation_id.is(id))
                .and(MessageTable_Table.sent.isNot(Boolean.FALSE))
                .and(MessageTable_Table.timestamp.greaterThan(0))

                .orderBy(OrderBy.fromProperty(MessageTable_Table.idMessage).descending()).limit(1).queryList();
        */
        return !CollectionUtils.isEmpty(messageList)?messageList.get(0):null;
    }

    public @Nullable Message getOldestMessage() {
        /* Last implementation
        List<MessageTable> oldestMessage = SQLite.select()
                .from(MessageTable.class)
                .where(MessageTable_Table.idConversation_id.is(id))
                .and(MessageTable_Table.timestamp.greaterThan(0))
                .orderBy(OrderBy.fromProperty(MessageTable_Table.timestamp).ascending()).limit(1).queryList();
                */
        return !CollectionUtils.isEmpty(messageList)?messageList.get(messageList.size()-1):null;
    }

    public void getFollowingMessages(@Nullable Message newestMessage, final BusinessCallback<List<Message>> callback) {
        if (newestMessage != null) {
            UVLiveApplication.getUVLiveGateway().getFollowingMessages(MessageMapper.getMessagesFormFromMessage(newestMessage),
                    new UVCallback<MessageListResponse>() {
                        @Override
                        public void onSuccess(@NonNull MessageListResponse messageListResponse) {
                            callback.onDataReceived(parseMessages(messageListResponse));
                        }

                        @Override
                        public void onError(int errorCode) {
                            callback.onError(ErrorMapper.mapError(errorCode));
                        }
                    });
        }
    }

    public void sendMessage(String messageText, final BusinessCallback<Message> businessCallback) {
        final Message message = new Message();

        message.setSent(Boolean.FALSE);
        message.setOwner(getOwnerName());
        message.setIdConversation(id);
        message.setTimestamp(Calendar.getInstance().getTimeInMillis());
        message.setMessage(messageText);

        MessageTable messageTable = MessageMapper.getMessageTableFromMessage(message);
        messageTable.save();

        messageList.add(message);
        businessCallback.onDataReceived(message);

        UVLiveApplication.getUVLiveGateway().sendMessage(MessageMapper.getMessageFormFromMessage(message),
                new UVCallback<BaseResponse>() {
            @Override
            public void onSuccess(@NonNull BaseResponse baseResponse) {
                message.setSent(true);
                MessageMapper.getMessageTableFromMessage(message).save();

                businessCallback.onDataReceived(message);
            }

            @Override
            public void onError(int errorCode) {

            }
        });
    }

    /*
    * Private methods
    * */

    private void saveMessage(Message message) {
        MessageMapper.getMessageTableFromMessage(message).save();
    }

    private void removeMessage(Message message) {
        SQLite.delete().from(MessageTable.class).where(MessageTable_Table.id.eq(message.getIdLocal()));
    }

    private List<Message> parseMessages(MessageListResponse messageListResponse) {
        List<Message> messages = MessageMapper.getMessageListFromMessageResponseList(id, messageListResponse.getMessages());
        for (Message message : messages) {
            if (!messageList.contains(message)) {
                saveMessage(message);
                messageList.add(message);
            } else if (!messageList.contains(message) ) {
                int pos = messageList.indexOf(message);
                messageList.get(pos).setSent(true);
                messageList.get(pos).setTimestamp(message.getTimestamp());
                saveMessage(message);
            }
        }

        Collections.sort(messageList);
        return messageList;
    }
}
