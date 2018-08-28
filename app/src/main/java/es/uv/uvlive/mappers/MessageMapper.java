package es.uv.uvlive.mappers;


import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import es.uv.uvlive.data.database.models.MessageTable;
import es.uv.uvlive.data.gateway.form.MessageForm;
import es.uv.uvlive.data.gateway.form.MessagesForm;
import es.uv.uvlive.data.gateway.response.MessageResponse;
import es.uv.uvlive.session.Message;
import es.uv.uvlive.ui.models.MessageModel;
import es.uv.uvlive.utils.DateUtils;

public final class MessageMapper {

    private MessageMapper() {
        throw new RuntimeException("Class can't be instatiated");
    }

    /*
    * List Mappers
    * */
    public static LinkedList<Message> getMessageListFromMessageResponseList(int idConversation, List<MessageTable> messageTableList) {
        LinkedList<Message> messageList = new LinkedList<>();

        for (MessageTable messageTable: messageTableList) {
            messageList.add(getMessageFromMessageTable(idConversation,messageTable));
        }

        return messageList;
    }

    public static List<Message> getMessageListFromMessageResponseList(int idConversation, @Nullable ArrayList<MessageResponse> messageResponse) {
        ArrayList<Message> messageList = new ArrayList<>();
        if (messageResponse != null) {
            for (MessageResponse messageModel : messageResponse) {
                messageList.add(getMessageFromMessageResponse(idConversation,messageModel));
            }
        }

        return messageList;
    }

    public static List<MessageModel> getMessageModelListFromMessageList(String ownerName, @NonNull List<Message> messageList) {
        List<MessageModel> messageModelList = new ArrayList<>();

        if (!messageList.isEmpty()) {
            for (Message message: messageList) {
                messageModelList.add(getMessageModelFromMessage(ownerName, message));
            }
        }

        return messageModelList;
    }

    /*
    * Individual Object Mappers
    * */

    public static Message getMessageFromMessageTable(int idConversation, MessageTable messageTable) {
        Message message = new Message();

        message.setIdConversation(idConversation);
        message.setIdLocal(messageTable.getId());
        message.setIdMessage(messageTable.getIdMessage());
        message.setTimestamp(messageTable.getTimestamp());
        message.setMessage(messageTable.getMessageText());
        message.setSent(messageTable.isSent());
        message.setOwner(messageTable.getOwner());
        message.setBlocked(messageTable.isBlocked());

        return message;
    }

    public static Message getMessageFromMessageResponse(int idConversation, MessageResponse messageResponse) {
        Message message = new Message();

        message.setMessage(messageResponse.getText());
        message.setIdMessage(messageResponse.getIdMessage());
        message.setTimestamp(messageResponse.getTimestamp());
        /* the response went from BE, then is sent */
        message.setSent(true);
        message.setOwner(messageResponse.getOwner());
        message.setIdConversation(idConversation);
        message.setBlocked(false);

        return message;
    }

    public static MessageModel getMessageModelFromMessage(@NonNull String ownerName, Message message) {
        MessageModel messageModel = new MessageModel();

        messageModel.setOwner(message.getOwner());
        messageModel.setMine(ownerName.equals(message.getOwner()));
        messageModel.setMessage(message.getMessage());
        messageModel.setSended(message.isSent());
        if (messageModel.isMine() && !message.isSent()) {
            messageModel.setDate(DateUtils.timestampToStringDate(message.getLocalTimestamp()));
        } else {
            messageModel.setDate(DateUtils.timestampToStringDate(message.getTimestamp()));
        }
        messageModel.setBlocked(message.isBlocked());

        return messageModel;
    }

    public static MessageTable getMessageTableFromMessage(Message message) {
        MessageTable messageTable = new MessageTable();

        if (message.getIdLocal() > 0) {
            messageTable.setId(message.getIdLocal());
        }
        if (message.getIdMessage() > 0) {
            messageTable.setIdMessage(message.getIdMessage());
        }
        if (message.getTimestamp() > 0) {
            messageTable.setTimestamp(message.getTimestamp());
        } else if (message.getLocalTimestamp() > 0) {
            messageTable.setTimestamp(message.getLocalTimestamp());
        } else {
            messageTable.setTimestamp(0);
        }
        messageTable.setMessageText(message.getMessage());
        messageTable.setSent(message.isSent());
        messageTable.setIdConversation(message.getIdLocal());
        messageTable.setOwner(message.getOwner());
        messageTable.setIdConversation(message.getIdConversation());
        messageTable.setBlocked(message.isBlocked());

        return messageTable;
    }

    public static MessageForm getMessageFormFromMessage(Message message) {
        MessageForm messageForm = new MessageForm();

        messageForm.setIdConversation(message.getIdConversation());
        messageForm.setMessage(message.getMessage());

        return messageForm;
    }
    public static MessagesForm getMessagesFormFromMessage(Message message) {
        MessagesForm messageForm = new MessagesForm();

        messageForm.setIdConversation(message.getIdConversation());
        messageForm.setTimestamp(message.getTimestamp());

        return messageForm;
    }
}
