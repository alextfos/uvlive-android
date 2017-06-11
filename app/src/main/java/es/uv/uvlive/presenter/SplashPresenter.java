package es.uv.uvlive.presenter;

import android.support.annotation.NonNull;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.List;

import es.uv.uvlive.UVLiveApplication;
import es.uv.uvlive.data.UVCallback;
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
            UVCallback<BaseResponse> callback = new UVCallback<BaseResponse>() {
                @Override
                public void onSuccess(@NonNull BaseResponse baseResponse) {

                }

                @Override
                public void onError(int errorCode) {
                    splashActions.onError(errorCode);
                }
            };
            PushTokenForm pushTokenForm = new PushTokenForm();
            pushTokenForm.setPushToken(UVLivePreferences.getInstance().getPushToken());
            UVLiveApplication.getUVLiveGateway().updatePushToken(pushTokenForm, callback);
            splashActions.onLogged();
        } else {
            splashActions.onNotLogged();
        }
    }
}
