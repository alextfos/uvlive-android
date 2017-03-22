package es.uv.uvlive.presenter;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import es.uv.uvlive.UVLiveApplication;
import es.uv.uvlive.data.gateway.form.LoginForm;
import es.uv.uvlive.data.gateway.response.LoginResponse;
import es.uv.uvlive.session.Admin;
import es.uv.uvlive.session.Student;
import es.uv.uvlive.session.Teacher;
import es.uv.uvlive.ui.actions.SessionActions;

/**
 * Created by alextfos on 01/12/2016.
 */

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

        Response.Listener<LoginResponse> responseListener = new Response.Listener<LoginResponse>() {
            @Override
            public void onResponse(LoginResponse loginResponse) {
                if (loginResponse.getErrorCode()==0) {
                    LoginModel loginModel = Binder.bindSession(loginResponse);
                    switch (typeLogin) {
                        case "Alumno":
                            currentUser = new Student();
                            break;
                        case "Profesor":
                            currentUser = new Teacher();
                            break;
                        case "Admin":
                            currentUser = new Admin();
                            break;
                    }
                    currentUser.setToken(loginResponse.getToken());
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

    private static class Binder {
        public static LoginModel bindSession(LoginResponse loginResponse) {

            if (loginResponse.getToken()== null) {
                return null;
            }


            LoginModel loginModel = new LoginModel();
            loginModel.setName(loginResponse.getUser());

            return loginModel;
        }
    }

    private static class LoginModel {
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
