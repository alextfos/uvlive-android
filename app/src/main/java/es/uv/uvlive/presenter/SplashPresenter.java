package es.uv.uvlive.presenter;

import android.support.annotation.NonNull;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import es.uv.uvlive.UVLiveApplication;
import es.uv.uvlive.data.gateway.response.StatusResponse;
import es.uv.uvlive.ui.actions.SplashActions;

/**
 * Created by alextfos on 22/01/2017.
 */

public class SplashPresenter extends BasePresenter {

    private SplashActions splashActions;

    public SplashPresenter(SplashActions splashActions) {
        this.splashActions = splashActions;
    }

    public void getStatus() {
        UVLiveApplication.getUVLiveGateway().status(new Response.Listener<StatusResponse>() {
            @Override
            public void onResponse(@NonNull StatusResponse statusResponse) {
                if (statusResponse.isStatus()) {
                    splashActions.onLogged();
                } else {
                    splashActions.onNotLogged();
                }
            }
        },new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                splashActions.onNotLogged();
            }
        });
    }
}
