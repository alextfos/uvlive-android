package com.example.atraverf.uvlive.gateway.form;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by atraverf on 25/04/16.
 */
public class ConversationsForm implements Parcelable {

    private String id;

    public ConversationsForm(Parcel in){
        id = in.readString();
    }

    public ConversationsForm(){

    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
    }

    public static final Creator<LoginForm> CREATOR = new Creator<LoginForm>() {
        public LoginForm createFromParcel(Parcel source) {
            return new LoginForm(source);
        }

        public LoginForm[] newArray(int size) {
            return new LoginForm[size];
        }
    };

    public void setId(String id) {
        this.id = id;
    }
}