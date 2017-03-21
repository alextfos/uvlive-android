package es.uv.uvlive.data.gateway.response;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by alextfos on 22/01/2017.
 */

public class StatusResponse extends BaseResponse implements Parcelable {

    private boolean status;

    protected StatusResponse(Parcel in) {
        super(in);
        status = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeByte((byte) (status ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<StatusResponse> CREATOR = new Creator<StatusResponse>() {
        @Override
        public StatusResponse createFromParcel(Parcel in) {
            return new StatusResponse(in);
        }

        @Override
        public StatusResponse[] newArray(int size) {
            return new StatusResponse[size];
        }
    };

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
