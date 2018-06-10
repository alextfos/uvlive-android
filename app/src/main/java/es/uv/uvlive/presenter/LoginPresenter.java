package es.uv.uvlive.presenter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;

import com.example.atraverf.uvlive.R;

import java.util.ArrayList;

import es.uv.uvlive.UVLiveApplication;
import es.uv.uvlive.data.UVCallback;
import es.uv.uvlive.data.UVLivePreferences;
import es.uv.uvlive.data.gateway.GsonRequest;
import es.uv.uvlive.data.gateway.form.LoginForm;
import es.uv.uvlive.data.gateway.response.LoginResponse;
import es.uv.uvlive.mappers.ErrorMapper;
import es.uv.uvlive.ui.actions.SessionActions;

public class LoginPresenter extends BasePresenter {

    /**
     * Class for loginTypes management
     */
    public enum LoginTypes {
        Student(R.string.logintypes_student),
        Teacher(R.string.logintypes_teacher),
        Admin(R.string.logintypes_admin),
        Merchant(R.string.logintypes_merchant);

        private int stringRes;

        LoginTypes(int stringRes) {
            this.stringRes = stringRes;
        }

        public int getStringRes() {
            return stringRes;
        }

        /**
         * Gets translated description lists from login types
         * @param context Requires context for getting descriptions' translation
         * @return translated list of loginTypes
         */
        public static String[] getLoginTypesDescriptions(Context context) {
            ArrayList<String> descriptions = new ArrayList<>();
            for (LoginTypes loginType : LoginTypes.values()) {
                descriptions.add(context.getResources().getString(loginType.getStringRes()));
            }

            return descriptions.toArray(new String[descriptions.size()]);
        }
    }

    private SessionActions sessionActions;

    public LoginPresenter(SessionActions sessionActions) {
        this.sessionActions = sessionActions;
    }

    public void login(String user, String password, int loginTypePosition) {
        final LoginTypes loginType = LoginTypes.values()[loginTypePosition];

        LoginForm request = new LoginForm();
        request.setUser(user);
        request.setPassword(password);
        request.setTypeLogin(loginType.name());
        request.setPushToken(UVLivePreferences.getInstance().getPushToken());
        
        UVCallback<LoginResponse> uvCallback = new UVCallback<LoginResponse>() {
            @Override
            public void onSuccess(@NonNull LoginResponse loginResponse) {
                GsonRequest.setToken(loginResponse.getToken());
                saveUser(loginType, loginResponse.getToken(), loginResponse.getOwnerField());
                sessionActions.loginOk(); // le pasamos el loginmodel
            }

            @Override
            public void onError(@StringRes int stringMessage) {
                sessionActions.onError(ErrorMapper.mapError(stringMessage));
            }
        };
        UVLiveApplication.getUVLiveGateway().login(request, uvCallback);
    }
}
