package es.uv.uvlive.ui.actions;

import es.uv.uvlive.data.gateway.response.ConversationsListResponse;
import es.uv.uvlive.data.gateway.response.LogListResponse;

/**
 * Created by atraver on 22/03/17.
 */

public interface LogsActions {
    void onLogsReceived(LogListResponse logListResponse);
}
