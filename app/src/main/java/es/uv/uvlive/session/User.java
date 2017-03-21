package es.uv.uvlive.session;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by alextfos on 07/12/2016.
 */

public class User implements Parcelable {
    private String token;

    public User() {

    }

    protected User(Parcel in) {
        token = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(token);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
