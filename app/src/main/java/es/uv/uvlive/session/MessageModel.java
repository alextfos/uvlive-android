package es.uv.uvlive.session;

import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import es.uv.uvlive.data.database.models.MessageTable;
import es.uv.uvlive.data.gateway.response.MessageResponse;
import es.uv.uvlive.utils.StringUtils;

public class MessageModel implements Comparable<MessageModel> {
    private int idLocal;
    private int idMessage;
    private int idConversation;
    private String message;
    private int timeStamp;
    private boolean sended;
    private String owner;

    public MessageModel(int idConversation,MessageResponse messageResponse) {
        message = messageResponse.getText();
        idMessage = messageResponse.getIdMessage();
        timeStamp = messageResponse.getTimestamp();
        sended = true;
        owner = messageResponse.getOwner();
        this.idConversation = idConversation;
    }

    public MessageModel(int idConversation, MessageTable messageTable) {
        this.idConversation = idConversation;
        idLocal = messageTable.getId();
        idMessage = messageTable.getIdMessage();
        timeStamp = messageTable.getTimeStamp();
        message = messageTable.getMessageText();
        sended = messageTable.isSended();
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

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(int timeStamp) {
        this.timeStamp = timeStamp;
    }

    public boolean isSended() {
        return sended;
    }

    public void setSended(boolean sended) {
        this.sended = sended;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

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

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof MessageModel) {
            if (this.idMessage == ((MessageModel) obj).idMessage ) {
                return true;
            } else if (this.idConversation == ((MessageModel) obj).idConversation &&
                this.idConversation == ((MessageModel) obj).idConversation &&
                    StringUtils.equals(this.message,((MessageModel) obj).message) &&
                    !this.sended == ((MessageModel) obj).sended) {
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
        if (idLocal == 0) {
            if (this.idMessage < o.idMessage) {
                return -1;
            } else if (this.idLocal > o.idLocal) {
                return 1;
            } else {
                return 0;
            }
        } else {
            if (this.idLocal < o.idLocal) {
                return -1;
            } else if (this.idLocal > o.idLocal) {
                return 1;
            } else {
                return 0;
            }
        }
    }
}
