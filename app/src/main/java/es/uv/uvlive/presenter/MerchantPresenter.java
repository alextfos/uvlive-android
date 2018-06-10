package es.uv.uvlive.presenter;

import android.support.annotation.NonNull;

import es.uv.uvlive.UVLiveApplication;
import es.uv.uvlive.data.UVCallback;
import es.uv.uvlive.data.gateway.form.BroadcastForm;
import es.uv.uvlive.data.gateway.response.BaseResponse;
import es.uv.uvlive.mappers.ErrorMapper;
import es.uv.uvlive.ui.actions.MerchantActions;

public class MerchantPresenter extends BasePresenter {

    private MerchantActions merchantActions;

    public MerchantPresenter(MerchantActions merchantActions) {
        this.merchantActions = merchantActions;
    }

    public void sendBroadcast(String title, String text) {
        BroadcastForm form = new BroadcastForm();

        form.setTitle(title);
        form.setBroadcastMessage(text);

        UVLiveApplication.getUVLiveGateway().registerBroadcast(form, new UVCallback<BaseResponse>() {
            @Override
            public void onSuccess(@NonNull BaseResponse baseResponse) {
                merchantActions.onOk();
            }

            @Override
            public void onError(int errorCode) {
                merchantActions.onError(ErrorMapper.mapError(errorCode));
            }
        });
    }
}
