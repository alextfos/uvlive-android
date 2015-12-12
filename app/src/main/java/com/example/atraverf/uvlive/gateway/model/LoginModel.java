package com.example.atraverf.uvlive.gateway.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by atraverf on 21/11/15.
 */
public class LoginModel implements Parcelable{

    public LoginModel(){}

    public LoginModel(Parcel in){

    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }

    public static final Creator<LoginModel> CREATOR = new Creator<LoginModel>() {
        public LoginModel createFromParcel(Parcel source) {
            return new LoginModel(source);
        }

        public LoginModel[] newArray(int size) {
            return new LoginModel[size];
        }
    };
}
