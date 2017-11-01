package es.uv.uvlive.session;

import android.support.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import es.uv.uvlive.data.database.models.MessageTable;
import es.uv.uvlive.data.gateway.response.MessageResponse;
import es.uv.uvlive.utils.NumberFormatUtils;
import es.uv.uvlive.utils.StringUtils;

public class MessageModel implements Comparable<MessageModel> {
    private int idLocal;
    private int idMessage;
    private int idConversation;
    private String message;
    private long timestamp;
    private boolean sent;
    private String owner;
    private String date;

    public static List<MessageModel> transform(int idConversation, List<MessageTable> messageTableList) {
        ArrayList<MessageModel> messageModelList = new ArrayList<>();

        for (MessageTable messageTable: messageTableList) {
            messageModelList.add(new MessageModel(idConversation, messageTable));
        }

        return messageModelList;
    }

    public static List<MessageModel> transform(int idConversation, @Nullable ArrayList<MessageResponse> messageResponse) {
        ArrayList<MessageModel> messageModelList = new ArrayList<>();
        if (messageResponse != null) {
            for (MessageResponse messageModel : messageResponse) {
                messageModelList.add(new MessageModel(idConversation,messageModel));
            }
        }

        return messageModelList;
    }

    public MessageModel(int idConversation,MessageResponse messageResponse) {
        message = messageResponse.getText();
        idMessage = messageResponse.getIdMessage();
        timestamp = messageResponse.getTimestamp();
        date = timestampToStringDate(timestamp);
        sent = true;
        owner = messageResponse.getOwner();
        this.idConversation = idConversation;

    private String timestampToStringDate(long timestamp) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timestamp);
        SimpleDateFormat format1 = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        return format1.format(calendar.getTime());
    }

    public MessageModel(int idConversation, MessageTable messageTable) {
        this.idConversation = idConversation;
        idLocal = messageTable.getId();
        idMessage = messageTable.getIdMessage();
        timestamp = messageTable.getTimestamp();
        date = timestampToStringDate(timestamp);
        message = messageTable.getMessageText();
        sent = messageTable.isSended();
        owner = messageTable.getOwner();
    }

    public int getIdLocal() {
        return idLocal;
    }

    public void setIdLocal(int idLocal) {
        this.idLocal = idLocal;
    }

    public int getIdMessage() {
        return idMessage;
    }

    public void setIdMessage(int idMessage) {
        this.idMessage = idMessage;
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

    public void setIdConversation(int idConversation) {
        this.idConversation = idConversation;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public boolean isSent() {
        return sent;
    }

    public void setSent(boolean sent) {
        this.sent = sent;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof MessageModel) {
            if (this.idMessage == ((MessageModel) obj).idMessage ) {
                return true;
            } else if (this.idConversation == ((MessageModel) obj).idConversation &&
                    StringUtils.equals(this.message,((MessageModel) obj).message) &&
                    !this.sent == ((MessageModel) obj).sent) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    @Override
    public int compareTo(MessageModel o) {
        return NumberFormatUtils.longToInt(this.timestamp - o.timestamp);
    }
}
