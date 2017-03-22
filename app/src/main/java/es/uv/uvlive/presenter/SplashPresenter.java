package es.uv.uvlive.presenter;

import android.support.annotation.NonNull;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;

import es.uv.uvlive.UVLiveApplication;
import es.uv.uvlive.data.gateway.GsonRequest;
import es.uv.uvlive.data.gateway.response.StatusResponse;
import es.uv.uvlive.ui.actions.SplashActions;
import es.uv.uvlive.utils.StringUtils;


public class SplashPresenter extends BasePresenter {

    private SplashActions splashActions;

    public SplashPresenter(SplashActions splashActions) {
        this.splashActions = splashActions;
    }

    public void getStatus() {
        loadUser();
        if (currentUser != null && !StringUtils.isBlank(currentUser.getToken())) {
            GsonRequest.setToken(currentUser.getToken());
            splashActions.onLogged();
        } else {
            splashActions.onNotLogged();
        }
//        UVLiveApplication.getUVLiveGateway().status(new Response.Listener<StatusResponse>() {
//            @Override
//            public void onResponse(@NonNull StatusResponse statusResponse) {
//                if (statusResponse.isStatus()) {
//                    splashActions.onLogged();
//                } else {
//                    splashActions.onNotLogged();
//                }
//            }
//        },new Response.ErrorListener(){
//            @Override
//            public void onErrorResponse(VolleyError volleyError) {
//                splashActions.onNotLogged();
//            }
//        });
    }
}
