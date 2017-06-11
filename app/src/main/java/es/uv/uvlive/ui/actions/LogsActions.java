package es.uv.uvlive.ui.actions;

import es.uv.uvlive.data.gateway.response.LogListResponse;

public interface LogsActions extends BaseActions {
    void onLogsReceived(LogListResponse logListResponse);
}
