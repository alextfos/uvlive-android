package es.uv.uvlive.presenter;

import android.support.annotation.NonNull;
import android.support.annotation.StringRes;

import es.uv.uvlive.UVLiveApplication;
import es.uv.uvlive.data.UVCallback;
import es.uv.uvlive.data.UVLivePreferences;
import es.uv.uvlive.data.gateway.GsonRequest;
import es.uv.uvlive.data.gateway.form.LoginForm;
import es.uv.uvlive.data.gateway.response.LoginResponse;
import es.uv.uvlive.session.Admin;
import es.uv.uvlive.session.Merchant;
import es.uv.uvlive.session.Student;
import es.uv.uvlive.session.Teacher;
import es.uv.uvlive.ui.actions.SessionActions;

public class LoginPresenter extends BasePresenter {

    private SessionActions sessionActions;

    public LoginPresenter(SessionActions sessionActions) {
        this.sessionActions = sessionActions;
    }

    public void login(String user, String password, final String typeLogin) {
        LoginForm request = new LoginForm();
        request.setUser(user);
        request.setPassword(password);
        request.setTypeLogin(typeLogin);
        request.setPushToken(UVLivePreferences.getInstance().getPushToken());

        UVCallback<LoginResponse> uvCallback = new UVCallback<LoginResponse>() {
            @Override
            public void onSuccess(@NonNull LoginResponse loginResponse) {
                    switch (typeLogin) { //TODO: remove hardcoded strings
                        case "Student":
                            currentUser = new Student();
                            break;
                        case "Teacher":
                            currentUser = new Teacher();
                            break;
                        case "Admin":
                            currentUser = new Admin();
                            break;
                        case "Merchant":
                            currentUser = new Merchant();
                    }
                    currentUser.setToken(loginResponse.getToken());
                    GsonRequest.setToken(loginResponse.getToken());
                    saveUser();
                    sessionActions.loginOk(); // le pasamos el loginmodel
            }

            @Override
            public void onError(@StringRes int stringMessage) {
                sessionActions.onError(stringMessage);
            }
        };
        UVLiveApplication.getUVLiveGateway().login(request, uvCallback);
    }
}
