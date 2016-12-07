package com.example.atraverf.uvlive.data.gateway.response;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by atraverf on 28/04/16.
 */
public class ConversationsListResponse implements Parcelable {

    private ArrayList<ConversationResponse> conversations;

    protected ConversationsListResponse(Parcel in) {
        if (this.conversations == null) {
            this.conversations = new ArrayList<ConversationResponse>();
        }
        in.readList(this.conversations, ConversationResponse.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(conversations);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public void setConversations(ArrayList<ConversationResponse> conversations) {
        this.conversations = conversations;
    }
    public ArrayList<ConversationResponse> getConversations() {
        return conversations;
    }

    public static final Creator<ConversationsListResponse> CREATOR = new Creator<ConversationsListResponse>() {
        @Override
        public ConversationsListResponse createFromParcel(Parcel in) {
            return new ConversationsListResponse(in);
        }

        @Override
        public ConversationsListResponse[] newArray(int size) {
            return new ConversationsListResponse[size];
        }
    };
}
