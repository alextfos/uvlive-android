package es.uv.uvlive.data.database.models;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ForeignKey;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import es.uv.uvlive.data.database.UVLiveDB;

@Table(database = UVLiveDB.class)
public class MessageTable extends BaseModel {

    @PrimaryKey(autoincrement = true)
    protected int id;

    @Column
    protected int idMessage;

    @Column
    protected long timestamp;

    @Column
    protected String messageText;

    @ForeignKey(tableClass = ConversationTable.class)
    protected long idConversation;

    @Column
    protected String owner;

    @Column
    protected boolean sent;

    @Column
    protected boolean blocked;

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public long getIdConversation() {
        return idConversation;
    }

    public void setIdConversation(long idConversation) {
        this.idConversation = idConversation;
    }

    public boolean isSent() {
        return sent;
    }

    public void setSent(boolean sent) {
        this.sent = sent;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdMessage() {
        return idMessage;
    }

    public void setIdMessage(int idMessage) {
        this.idMessage = idMessage;
    }

    public String getOwner() {
        return owner;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
}
