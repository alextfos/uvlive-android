package es.uv.uvlive.mappers;


import android.support.annotation.StringRes;

import com.example.atraverf.uvlive.R;

import java.util.HashMap;

import es.uv.uvlive.session.BusinessError;

import static es.uv.uvlive.data.gateway.BackendResponseCodes.ERROR_CODE_CONVERSATION_NOT_CREATED;
import static es.uv.uvlive.data.gateway.BackendResponseCodes.ERROR_CODE_MERCHANT_NOT_EXISTS_EXCEPTION;
import static es.uv.uvlive.data.gateway.BackendResponseCodes.ERROR_CODE_TOKEN_EXPIRED;
import static es.uv.uvlive.data.gateway.BackendResponseCodes.ERROR_CODE_UNAUTHORIZED;
import static es.uv.uvlive.data.gateway.BackendResponseCodes.ERROR_CODE_UNKNOWN;
import static es.uv.uvlive.data.gateway.BackendResponseCodes.ERROR_CODE_USER_BLOCKED;
import static es.uv.uvlive.data.gateway.BackendResponseCodes.ERROR_CODE_USER_DEFINED;
import static es.uv.uvlive.data.gateway.BackendResponseCodes.ERROR_CODE_USER_NOT_DEFINED;
import static es.uv.uvlive.data.gateway.BackendResponseCodes.ERROR_CODE_VALIDATION_FORM;
import static es.uv.uvlive.data.gateway.BackendResponseCodes.ERROR_CODE_WRONG_CREDENTIALS;
import static es.uv.uvlive.session.BusinessError.CONVERSATION_NOT_EXISTS;
import static es.uv.uvlive.session.BusinessError.ERROR_GETTING_CONVERSATION;
import static es.uv.uvlive.session.BusinessError.GENERIC_ERROR;
import static es.uv.uvlive.session.BusinessError.LOGIN_INVALID;
import static es.uv.uvlive.session.BusinessError.MERCHANT_NOT_EXISTS;
import static es.uv.uvlive.session.BusinessError.OK;
import static es.uv.uvlive.session.BusinessError.PARAMETERS_INCORRECT;
import static es.uv.uvlive.session.BusinessError.SESSION_EXPIRED;
import static es.uv.uvlive.session.BusinessError.UNAUTHORIZED;
import static es.uv.uvlive.session.BusinessError.USER_BLOCKED;
import static es.uv.uvlive.session.BusinessError.USER_DEFINED;
import static es.uv.uvlive.session.BusinessError.USER_NOT_DEFINED;

public class ErrorMapper {

    public static final @StringRes int ERROR_MESSAGE_OK = 0;
    public static final @StringRes int ERROR_MESSAGE_UNKNOWN = R.string.error_unknown;
    public static final @StringRes int ERROR_MESSAGE_WRONG_CREDENTIALS= R.string.error_wrong_credentials;
    public static final @StringRes int ERROR_MESSAGE_UNAUTHORIZED = R.string.error_unauthorized;
    public static final @StringRes int ERROR_MESSAGE_TOKEN_EXPIRED = R.string.error_token_expired;
    public static final @StringRes int ERROR_MESSAGE_USER_DEFINED = R.string.error_user_defined;
    public static final @StringRes int ERROR_MESSAGE_USER_NOT_DEFINED = R.string.error_user_not_defined;
    public static final @StringRes int ERROR_MESSAGE_PARAMETERS_INCORRECT = R.string.error_parameters_incorrect;
    public static final @StringRes int ERROR_MESSAGE_CONVERSATION_NOT_EXISTS = R.string.error_conversation_not_exists;
    public static final @StringRes int ERROR_MESSAGE_USER_BLOCKED = R.string.error_user_blocked;
    public static final @StringRes int ERROR_MESSAGE_MERCHANT_NOT_EXISTS = R.string.error_merchant_not_exists;
    public static final @StringRes int ERROR_MESSAGE_GETTING_CONVERSATION = R.string.error_getting_conversation;

    private static HashMap<BusinessError, Integer> errorMessages = new HashMap<>();
    private static HashMap<Integer, BusinessError> conversionErrorMap = new HashMap<>();

    static {
        conversionErrorMap.put(ERROR_CODE_UNKNOWN.getErrorCode(), GENERIC_ERROR);
        conversionErrorMap.put(ERROR_CODE_WRONG_CREDENTIALS.getErrorCode(), LOGIN_INVALID);
        conversionErrorMap.put(ERROR_CODE_UNAUTHORIZED.getErrorCode(), UNAUTHORIZED);
        conversionErrorMap.put(ERROR_CODE_TOKEN_EXPIRED.getErrorCode(), SESSION_EXPIRED);
        conversionErrorMap.put(ERROR_CODE_USER_DEFINED.getErrorCode(), USER_DEFINED);
        conversionErrorMap.put(ERROR_CODE_USER_NOT_DEFINED.getErrorCode(), USER_NOT_DEFINED);
        conversionErrorMap.put(ERROR_CODE_VALIDATION_FORM.getErrorCode(), PARAMETERS_INCORRECT);
        conversionErrorMap.put(ERROR_CODE_CONVERSATION_NOT_CREATED.getErrorCode(), CONVERSATION_NOT_EXISTS);
        conversionErrorMap.put(ERROR_CODE_USER_BLOCKED.getErrorCode(), USER_BLOCKED);
        conversionErrorMap.put(ERROR_CODE_MERCHANT_NOT_EXISTS_EXCEPTION.getErrorCode(), MERCHANT_NOT_EXISTS);

        errorMessages.put(GENERIC_ERROR, ERROR_MESSAGE_UNKNOWN);
        errorMessages.put(OK, ERROR_MESSAGE_OK);
        errorMessages.put(LOGIN_INVALID, ERROR_MESSAGE_WRONG_CREDENTIALS);
        errorMessages.put(UNAUTHORIZED, ERROR_MESSAGE_UNAUTHORIZED);
        errorMessages.put(SESSION_EXPIRED, ERROR_MESSAGE_TOKEN_EXPIRED);
        errorMessages.put(USER_DEFINED, ERROR_MESSAGE_USER_DEFINED);
        errorMessages.put(USER_NOT_DEFINED, ERROR_MESSAGE_USER_NOT_DEFINED);
        errorMessages.put(PARAMETERS_INCORRECT, ERROR_MESSAGE_PARAMETERS_INCORRECT);
        errorMessages.put(CONVERSATION_NOT_EXISTS, ERROR_MESSAGE_CONVERSATION_NOT_EXISTS);
        errorMessages.put(USER_BLOCKED, ERROR_MESSAGE_USER_BLOCKED);
        errorMessages.put(MERCHANT_NOT_EXISTS, ERROR_MESSAGE_MERCHANT_NOT_EXISTS);
        errorMessages.put(ERROR_GETTING_CONVERSATION, ERROR_MESSAGE_GETTING_CONVERSATION);

    }

    public static @StringRes int getMessage(BusinessError errorCode) {
        if (errorMessages.containsKey(errorCode)) {
            return errorMessages.get(errorCode);
        } else {
            return ERROR_MESSAGE_UNKNOWN;
        }
    }

    public static BusinessError mapError(int errorCode) {
        return conversionErrorMap.get(errorCode);
    }
}
