package es.uv.uvlive.presenter;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import es.uv.uvlive.UVLiveApplication;
import es.uv.uvlive.data.UVLivePreferences;
import es.uv.uvlive.data.gateway.GsonRequest;
import es.uv.uvlive.data.gateway.form.LoginForm;
import es.uv.uvlive.data.gateway.response.LoginResponse;
import es.uv.uvlive.session.Admin;
import es.uv.uvlive.session.Businessman;
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

        Response.Listener<LoginResponse> responseListener = new Response.Listener<LoginResponse>() {
            @Override
            public void onResponse(LoginResponse loginResponse) {
                if (loginResponse.getErrorCode() == 0) {
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
                        case "Businessman":
                            currentUser = new Businessman();
                    }
                    currentUser.setToken(loginResponse.getToken());
                    GsonRequest.setToken(loginResponse.getToken());
                    saveUser();
                    sessionActions.loginOk(); // le pasamos el loginmodel
                } else {
                    sessionActions.loginError();
                }
            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                sessionActions.loginError();
            }
        };
        UVLiveApplication.getUVLiveGateway().login(request, responseListener, errorListener);
    }
}
