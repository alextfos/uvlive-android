package es.uv.uvlive.data.gateway;


public enum BackendResponseCodes {
    ERROR_CODE_UNKNOWN(1),
    ERROR_CODE_OK(0),
    ERROR_CODE_WRONG_CREDENTIALS (-1),
    ERROR_CODE_UNAUTHORIZED(-2),
    ERROR_CODE_TOKEN_EXPIRED(-3),
    ERROR_CODE_USER_DEFINED(-4),
    ERROR_CODE_USER_NOT_DEFINED(-5),
    ERROR_CODE_VALIDATION_FORM(-6),
    ERROR_CODE_CONVERSATION_NOT_CREATED(-7),
    ERROR_CODE_USER_BLOCKED(-8),
    ERROR_CODE_MERCHANT_NOT_EXISTS_EXCEPTION(-9);

    private int errorCode;

    BackendResponseCodes(int code) {
        errorCode = code;
    }


    public int getErrorCode() {
        return errorCode;
    }
}
