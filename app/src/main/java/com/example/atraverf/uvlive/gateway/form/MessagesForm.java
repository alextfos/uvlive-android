package com.example.atraverf.uvlive.gateway.form;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by atraverf on 25/04/16.
 */
public class MessagesForm implements Parcelable {

    private int idConversation;
    private int lastMessage;

    public MessagesForm(){}

    protected MessagesForm(Parcel in) {
        idConversation = in.readInt();
        lastMessage = in.readInt();
    }

    public static final Creator<MessagesForm> CREATOR = new Creator<MessagesForm>() {
        @Override
        public MessagesForm createFromParcel(Parcel in) {
            return new MessagesForm(in);
        }

        @Override
        public MessagesForm[] newArray(int size) {
            return new MessagesForm[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(idConversation);
        dest.writeInt(lastMessage);
    }

    public void setIdConversation(int idConversation) {
        this.idConversation = idConversation;
    }
    public void setLastMessage(int lastMessage){
        this.lastMessage = lastMessage;
    }
}
