package es.uv.uvlive.session;

import android.support.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import es.uv.uvlive.data.database.models.MessageTable;
import es.uv.uvlive.data.gateway.response.MessageResponse;
import es.uv.uvlive.utils.DateUtils;
import es.uv.uvlive.utils.NumberFormatUtils;
import es.uv.uvlive.utils.StringUtils;

public class Message implements Comparable<Message> {
    private int idLocal;
    private int idMessage;
    private int idConversation;
    private String message;
    private long timestamp;
    private boolean sent;
    private String owner;

    public Message() {
        idLocal = -1;
        idMessage = -1;
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

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Message) {
            if (this.idMessage == ((Message) obj).idMessage ) {
                return true;
            } else if (this.idConversation == ((Message) obj).idConversation &&
                    StringUtils.equals(this.message,((Message) obj).message) &&
                    ((Message)obj).timestamp == timestamp) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    @Override
    public int compareTo(Message o) {
        return NumberFormatUtils.longToInt(this.timestamp - o.timestamp);
    }
}
