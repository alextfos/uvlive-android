package com.example.atraverf.uvlive.data.gateway.response;

import android.os.Parcel;

/**
 * Created by atraverf on 21/11/15.
 */
public class LoginResponse extends BaseResponse {

    private String user;
    public LoginResponse(){
        super();
    }

    public LoginResponse(Parcel in){
        super(in);
        this.user = in.readString();
    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.user);
    }

    public static final Creator<LoginResponse> CREATOR = new Creator<LoginResponse>() {
        public LoginResponse createFromParcel(Parcel source) {
            return new LoginResponse(source);
        }

        public LoginResponse[] newArray(int size) {
            return new LoginResponse[size];
        }
    };
}
