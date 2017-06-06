package es.uv.uvlive.presenter;

import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.List;

import es.uv.uvlive.UVLiveApplication;
import es.uv.uvlive.data.UVLivePreferences;
import es.uv.uvlive.data.database.models.ConversationTable;
import es.uv.uvlive.data.gateway.GsonRequest;
import es.uv.uvlive.data.gateway.form.PushTokenForm;
import es.uv.uvlive.data.gateway.response.BaseResponse;
import es.uv.uvlive.data.gateway.response.ConversationsListResponse;
import es.uv.uvlive.session.ConversationModel;
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

            Response.Listener<BaseResponse> responseListener = new Response.Listener<BaseResponse>() {
                @Override
                public void onResponse(BaseResponse baseResponse) {
                    Log.d("","Token updated");
                }

            };
            Response.ErrorListener errorListener = new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    Log.d("proves", "Update token - Error");
                }
            };
            PushTokenForm pushTokenForm = new PushTokenForm();
            pushTokenForm.setPushToken(UVLivePreferences.getInstance().getPushToken());
            UVLiveApplication.getUVLiveGateway().updatePushToken(pushTokenForm,
                    responseListener, errorListener);
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
