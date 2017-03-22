package es.uv.uvlive.presenter;

import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import es.uv.uvlive.UVLiveApplication;
import es.uv.uvlive.data.gateway.response.LogListResponse;
import es.uv.uvlive.ui.actions.LogsActions;

/**
 * Created by atraver on 22/03/17.
 */

public class LogsPresenter {

    private LogsActions logsActions;

    public LogsPresenter(LogsActions logsActions) {
        this.logsActions = logsActions;
    }

    public void getLogs() {
        Response.Listener<LogListResponse> responseListener = new Response.Listener<LogListResponse>() {
            @Override
            public void onResponse(LogListResponse logListResponse) {
                logsActions.onLogsReceived(logListResponse);
            }
        };
        Response.ErrorListener errorListener = new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.d("proves", "Conversaciones - Error");
            }
        };
        UVLiveApplication.getUVLiveGateway().logs(responseListener, errorListener);
    }
}
