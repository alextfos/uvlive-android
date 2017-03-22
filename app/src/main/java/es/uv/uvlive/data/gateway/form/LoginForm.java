package es.uv.uvlive.data.gateway.form;

import android.os.Parcel;
import android.os.Parcelable;

public class LoginForm {

    private String userName;
    private String password;
    private String loginType;

    public void setUser(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setTypeLogin(String type) {
        this.loginType = type;
    }
}
