package es.uv.uvlive.session;

import es.uv.uvlive.utils.NumberFormatUtils;
import es.uv.uvlive.utils.StringUtils;

public class Message implements Comparable<Message> {
    private int idLocal;
    private int idMessage;
    private int idConversation;
    private String message;
    private long timestamp;
    private long localTimestamp;
    private boolean sent;
    private String owner;
    private boolean blocked;

    public Message() {
        idLocal = -1;
        idMessage = -1;
        timestamp = -1;
        localTimestamp = -1;
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

    public int getIdConversation() {
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

    public long getLocalTimestamp() {
        return localTimestamp;
    }

    public void setLocalTimestamp(long localTimestamp) {
        this.localTimestamp = localTimestamp;
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

    public boolean isBlocked() {
        return blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj) || (obj instanceof Message && this.idMessage == ((Message) obj).idMessage) ||
                (obj instanceof Message &&
                        idConversation == ((Message) obj).idConversation &&
                        StringUtils.equals(this.message,((Message) obj).message) &&
                        ((Message)obj).timestamp == timestamp);
    }

    @Override
    public int compareTo(Message o) {
        return NumberFormatUtils.longToInt(o.timestamp - this.timestamp);
    }
}
