package es.uv.uvlive.service;

import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import es.uv.uvlive.data.UVLivePreferences;
import es.uv.uvlive.data.gateway.GsonRequest;
import es.uv.uvlive.data.gateway.UVLiveGateway;
import es.uv.uvlive.data.gateway.response.BaseResponse;

/**
 * Created by alextfos on 19/12/2016.
 */

public class UVLiveInstanceIDService extends FirebaseInstanceIdService {

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d("","Token: "+refreshedToken);
        UVLivePreferences.getInstance(getApplicationContext()).savePushToken(refreshedToken);
        if (GsonRequest.hasToken()) { // Exists a valid session
            new UVLiveGateway(getApplicationContext()).updatePushToken(refreshedToken, new Response.Listener<BaseResponse>() {
                @Override
                public void onResponse(BaseResponse baseResponse) {

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {

                }
            });
        }
    }
}
