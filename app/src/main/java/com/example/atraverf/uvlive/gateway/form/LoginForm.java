package com.example.atraverf.uvlive.gateway.form;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.Editable;

/**
 * Created by atraverf on 21/11/15.
 */
public class LoginForm implements Parcelable {

    private String userName;
    //private String password;

    public LoginForm(Parcel in){
        userName = in.readString();
        //password = in.readString();
    }

    public LoginForm(){

    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(userName);
        //dest.writeString(password);
    }

    public static final Creator<LoginForm> CREATOR = new Creator<LoginForm>() {
        public LoginForm createFromParcel(Parcel source) {
            return new LoginForm(source);
        }

        public LoginForm[] newArray(int size) {
            return new LoginForm[size];
        }
    };

    public void setUser(String userName) {
        this.userName = userName;
    }

    /*public void setPassword(String password) {
        this.password = password;
    }*/
}