package es.uv.uvlive.data.gateway.response;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by atraverf on 21/11/15.
 */
public class LoginResponse extends BaseResponse implements Parcelable {

    private String user;
    private String token;

    public LoginResponse() {
        super();
    }

    protected LoginResponse(Parcel in) {
        super(in);
        user = in.readString();
        token = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(user);
        dest.writeString(token);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<LoginResponse> CREATOR = new Creator<LoginResponse>() {
        @Override
        public LoginResponse createFromParcel(Parcel in) {
            return new LoginResponse(in);
        }

        @Override
        public LoginResponse[] newArray(int size) {
            return new LoginResponse[size];
        }
    };

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
