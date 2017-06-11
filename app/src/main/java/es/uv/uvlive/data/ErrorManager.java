package es.uv.uvlive.data;


import android.support.annotation.StringRes;

import com.example.atraverf.uvlive.R;

import java.util.HashMap;

public class ErrorManager {
    public static final int ERROR_CODE_UNKNOWN = 1;
    public static final int ERROR_CODE_OK = 0;
    public static final int ERROR_CODE_WRONG_CREDENTIALS = -1;
    public static final int ERROR_CODE_UNAUTHORIZED = -2;
    public static final int ERROR_CODE_TOKEN_EXPIRED = -3;

    public static final @StringRes int ERROR_MESSAGE_UNKNOWN = R.string.error_unknown;
    public static final @StringRes int ERROR_MESSAGE_OK = 0;
    public static final @StringRes int ERROR_MESSAGE_WRONG_CREDENTIALS= R.string.error_wrong_credentials;
    public static final @StringRes int ERROR_MESSAGE_UNAUTHORIZED = R.string.error_unauthorized;
    public static final @StringRes int ERROR_MESSAGE_TOKEN_EXPIRED = R.string.error_token_expired;

    private static HashMap<Integer,Integer> errorMessages = new HashMap<>();

    static {
        errorMessages.put(ERROR_CODE_UNKNOWN,ERROR_MESSAGE_UNKNOWN);
        errorMessages.put(ERROR_CODE_OK,ERROR_MESSAGE_OK);
        errorMessages.put(ERROR_CODE_WRONG_CREDENTIALS,ERROR_MESSAGE_WRONG_CREDENTIALS);
        errorMessages.put(ERROR_CODE_UNAUTHORIZED,ERROR_MESSAGE_UNAUTHORIZED);
        errorMessages.put(ERROR_CODE_TOKEN_EXPIRED,ERROR_MESSAGE_TOKEN_EXPIRED);

    }

    public static @StringRes int getMessage(int errorCode) {
        if (errorMessages.containsKey(errorCode)) {
            return errorMessages.get(errorCode);
        } else {
            return ERROR_MESSAGE_UNKNOWN;
        }
    }
}
