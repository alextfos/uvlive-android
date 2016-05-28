package com.example.atraverf.uvlive.gateway.response;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by atraverf on 25/04/16.
 */
public class MessagesResponse implements Parcelable {
    protected MessagesResponse(Parcel in) {
    }

    public static final Creator<MessagesResponse> CREATOR = new Creator<MessagesResponse>() {
        @Override
        public MessagesResponse createFromParcel(Parcel in) {
            return new MessagesResponse(in);
        }

        @Override
        public MessagesResponse[] newArray(int size) {
            return new MessagesResponse[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }
}
