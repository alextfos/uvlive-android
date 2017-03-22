package es.uv.uvlive.session;

import java.util.ArrayList;
import java.util.List;

import es.uv.uvlive.data.database.models.MessageTable;
import es.uv.uvlive.data.gateway.response.MessageResponse;

public class MessageModel {
    private String id;
    private long idConversation;
    private String message;
    private long timeStamp;
    private boolean sended;

    public MessageModel(MessageResponse messageResponse) {
        message = messageResponse.getMessage();
        sended = true;
    }

    public MessageModel(MessageTable messageTable) {
        message = messageTable.getMessageText();
        sended = messageTable.isSended();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getIdConversation() {
        return idConversation;
    }

    public void setIdConversation(long idConversation) {
        this.idConversation = idConversation;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public boolean isSended() {
        return sended;
    }

    public void setSended(boolean sended) {
        this.sended = sended;
    }

    public static List<MessageModel> transform(List<MessageTable> messageTableList) {
        ArrayList<MessageModel> messageModelList = new ArrayList<>();

        for (MessageTable messageTable: messageTableList) {
            messageModelList.add(new MessageModel(messageTable));
        }

        return messageModelList;
    }

    public static List<MessageModel> transform(ArrayList<MessageResponse> messageResponse) {
        ArrayList<MessageModel> messageModelList = new ArrayList<>();

        for (MessageResponse messageModel: messageResponse) {
            messageModelList.add(new MessageModel(messageModel));
        }

        return messageModelList;
    }
}
