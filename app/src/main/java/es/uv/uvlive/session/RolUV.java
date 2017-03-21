package es.uv.uvlive.session;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by alextfos on 07/12/2016.
 */

public class RolUV extends User implements Parcelable {

    private String pushToken;

    public RolUV() {

    }

    protected RolUV(Parcel in) {
        super(in);
        pushToken = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(pushToken);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<RolUV> CREATOR = new Creator<RolUV>() {
        @Override
        public RolUV createFromParcel(Parcel in) {
            return new RolUV(in);
        }

        @Override
        public RolUV[] newArray(int size) {
            return new RolUV[size];
        }
    };

    public String getPushToken() {
        return pushToken;
    }

    public void setPushToken(String pushToken) {
        this.pushToken = pushToken;
    }
}
