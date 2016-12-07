package com.example.atraverf.uvlive.presenter;

import android.content.pm.PackageInstaller;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.atraverf.uvlive.UVLiveApplication;
import com.example.atraverf.uvlive.gateway.form.LoginForm;
import com.example.atraverf.uvlive.gateway.response.LoginResponse;
import com.example.atraverf.uvlive.ui.actions.SessionActions;

/**
 * Created by alextfos on 01/12/2016.
 */

public class SessionPresenter {

    private SessionActions sessionActions;

    public SessionPresenter (SessionActions sessionActions) {
        this.sessionActions = sessionActions;
    }

    public void login(String user, String password, String typeLogin) {
        LoginForm request = new LoginForm();
        request.setUser(user);
        request.setPassword(password);
        request.setTypeLogin(typeLogin);

        Response.Listener<LoginResponse> responseListener = new Response.Listener<LoginResponse>() {
            @Override
            public void onResponse(LoginResponse loginResponse) {
                if (loginResponse.getErrorCode()==0) {
                    sessionActions.loginOk();
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
