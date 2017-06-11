package es.uv.uvlive.presenter;

import android.support.annotation.NonNull;

import es.uv.uvlive.UVLiveApplication;
import es.uv.uvlive.data.UVCallback;
import es.uv.uvlive.data.gateway.response.LogListResponse;
import es.uv.uvlive.ui.actions.LogsActions;

public class LogsPresenter {

    private LogsActions logsActions;

    public LogsPresenter(LogsActions logsActions) {
        this.logsActions = logsActions;
    }

    public void getLogs() {
        UVLiveApplication.getUVLiveGateway().logs(new UVCallback<LogListResponse>() {
            @Override
            public void onSuccess(@NonNull LogListResponse logListResponse) {
                logsActions.onLogsReceived(logListResponse);
            }

            @Override
            public void onError(int errorCode) {
                logsActions.onError(errorCode);
            }
        });
    }
}
