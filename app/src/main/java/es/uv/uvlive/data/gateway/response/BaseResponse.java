package es.uv.uvlive.data.gateway.response;

import com.google.gson.Gson;

public class BaseResponse {
    private int errorCode;

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
