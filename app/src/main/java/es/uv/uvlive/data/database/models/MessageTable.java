package es.uv.uvlive.data.database.models;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ForeignKey;
import com.raizlabs.android.dbflow.annotation.NotNull;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.annotation.Unique;
import com.raizlabs.android.dbflow.structure.BaseModel;

import es.uv.uvlive.data.database.UVLiveDB;

/**
 * Created by atraver on 21/03/17.
 */
@Table(database = UVLiveDB.class)
public class MessageTable extends BaseModel {

    @PrimaryKey(autoincrement = true)
    long id;

    @NotNull
    @Unique
    @Column
    long timeStamp;

    @Column
    String messageText;

    @ForeignKey(tableClass = ConversationTable.class)
    long idConversation;

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
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
}
