package es.uv.uvlive.service;

import android.support.annotation.NonNull;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import es.uv.uvlive.data.UVCallback;
import es.uv.uvlive.data.UVLivePreferences;
import es.uv.uvlive.data.gateway.GsonRequest;
import es.uv.uvlive.data.gateway.UVLiveGateway;
import es.uv.uvlive.data.gateway.form.PushTokenForm;
import es.uv.uvlive.data.gateway.response.BaseResponse;

public class UVLiveInstanceIDService extends FirebaseInstanceIdService {

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d("","Token: "+refreshedToken);
        UVLivePreferences.getInstance(getApplicationContext()).savePushToken(refreshedToken);
        if (GsonRequest.hasToken()) { // Exists a valid session
            PushTokenForm pushTokenForm = new PushTokenForm();
            pushTokenForm.setPushToken(refreshedToken);
            UVCallback<BaseResponse> callback = new UVCallback<BaseResponse>() {
                @Override
                public void onSuccess(@NonNull BaseResponse baseResponse) {

                }

                @Override
                public void onError(int errorCode) {

                }
            };
            new UVLiveGateway(getApplicationContext()).updatePushToken(pushTokenForm, callback);
        }
    }
}
