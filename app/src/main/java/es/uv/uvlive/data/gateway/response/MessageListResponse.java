package es.uv.uvlive.data.gateway.response;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by atraverf on 25/04/16.
 */
public class MessageListResponse implements Parcelable {
    private ArrayList<MessageResponse> messageResponse;

    public MessageListResponse() {

    }

    protected MessageListResponse(Parcel in) {
    }

    public static final Creator<MessageListResponse> CREATOR = new Creator<MessageListResponse>() {
        @Override
        public MessageListResponse createFromParcel(Parcel in) {
            return new MessageListResponse(in);
        }

        @Override
        public MessageListResponse[] newArray(int size) {
            return new MessageListResponse[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

    public ArrayList<MessageResponse> getMessageResponse() {
        return messageResponse;
    }

    public void setMessageResponse(ArrayList<MessageResponse> messageResponse) {
        this.messageResponse = messageResponse;
    }
}
