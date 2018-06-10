package es.uv.uvlive.presenter;

import android.support.annotation.NonNull;

import es.uv.uvlive.UVLiveApplication;
import es.uv.uvlive.data.UVCallback;
import es.uv.uvlive.data.UVLivePreferences;
import es.uv.uvlive.data.gateway.GsonRequest;
import es.uv.uvlive.data.gateway.form.PushTokenForm;
import es.uv.uvlive.data.gateway.response.BaseResponse;
import es.uv.uvlive.mappers.ErrorMapper;
import es.uv.uvlive.session.RolUV;
import es.uv.uvlive.ui.actions.SplashActions;
import es.uv.uvlive.utils.StringUtils;

public class SplashPresenter extends BasePresenter {

    private SplashActions splashActions;

    public SplashPresenter(SplashActions splashActions) {
        this.splashActions = splashActions;
    }

    public void getStatus() {
        loadUser();
        if (getUser() != null && !StringUtils.isBlank(getUser().getToken())) {
            GsonRequest.setToken(getUser().getToken());
            UVCallback<BaseResponse> callback = new UVCallback<BaseResponse>() {
                @Override
                public void onSuccess(@NonNull BaseResponse baseResponse) {

                }

                @Override
                public void onError(int errorCode) {
                    splashActions.onError(ErrorMapper.mapError(errorCode));
                }
            };
            String pushToken = UVLivePreferences.getInstance().getPushToken();
            if (getUser() instanceof RolUV && !StringUtils.isBlank(pushToken)) {
                PushTokenForm pushTokenForm = new PushTokenForm();
                pushTokenForm.setPushToken(pushToken);

                UVLiveApplication.getUVLiveGateway().updatePushToken(pushTokenForm, callback);
            }
            splashActions.onLogged();
        } else {
            splashActions.onNotLogged();
        }
    }
}
