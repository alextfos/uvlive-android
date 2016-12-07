package com.example.atraverf.uvlive.data.gateway.response;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by atraverf on 25/04/16.
 */
public class ConversationResponse implements Parcelable {

    private int id;
    private String name;

    public ConversationResponse(){}

    protected ConversationResponse(Parcel in) {
        id = in.readInt();
        name = in.readString();
    }

    public static final Creator<ConversationResponse> CREATOR = new Creator<ConversationResponse>() {
        @Override
        public ConversationResponse createFromParcel(Parcel in) {
            return new ConversationResponse(in);
        }

        @Override
        public ConversationResponse[] newArray(int size) {
            return new ConversationResponse[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
    }

    public int getId(){
        return id;
    }

    public String getName(){
        return name;
    }
}
