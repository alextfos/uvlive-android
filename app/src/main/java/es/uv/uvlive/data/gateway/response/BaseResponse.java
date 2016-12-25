package es.uv.uvlive.data.gateway.response;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by alextfos on 24/11/2016.
 */

public class BaseResponse implements Parcelable {
    private int errorCode;

    public BaseResponse() {

    }
    protected BaseResponse(Parcel in) {
        errorCode = in.readInt();
    }

    public static final Creator<BaseResponse> CREATOR = new Creator<BaseResponse>() {
        @Override
        public BaseResponse createFromParcel(Parcel in) {
            return new BaseResponse(in);
        }

        @Override
        public BaseResponse[] newArray(int size) {
            return new BaseResponse[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(errorCode);
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }
}
