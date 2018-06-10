package es.uv.uvlive.data.gateway;


public enum BackendResponseCodes {
    ERROR_CODE_UNKNOWN(1),
    ERROR_CODE_OK(0),
    ERROR_CODE_WRONG_CREDENTIALS (-1),
    ERROR_CODE_UNAUTHORIZED(-2),
    ERROR_CODE_TOKEN_EXPIRED(-3);

    private int errorCode;

    BackendResponseCodes(int code) {
        errorCode = code;
    }


    public int getErrorCode() {
        return errorCode;
    }
}
