package es.uv.uvlive.data.gateway;

import android.content.Context;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.android.volley.RequestQueue;
import es.uv.uvlive.data.gateway.form.LoginForm;
import es.uv.uvlive.data.gateway.form.MessagesForm;
import es.uv.uvlive.data.gateway.response.ConversationsListResponse;
import es.uv.uvlive.data.gateway.response.LogListResponse;
import es.uv.uvlive.data.gateway.response.LoginResponse;
import es.uv.uvlive.data.gateway.response.MessageListResponse;
import es.uv.uvlive.data.gateway.response.MessageResponse;
import es.uv.uvlive.data.gateway.response.StatusResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

/**
 * Created by atraverf on 17/11/15.
 */
public class UVLiveGateway {

    private static final String TAG = "UVLiveGateway";
    private static final String sLoginUrl = "/login";
    private static final String sConversationsUrl ="/conversations";
    private static final String sLoggerUrl ="/logger";
    private static final String sStatus = "/status";

//    private static String environment = "http://10.0.2.2:8080/uvlive-api-1.0-SNAPSHOT";
    private static String environment = "http://slopez.uv.es:8080/uvlive-api-1.0-SNAPSHOT";
    public static final Gson GSON_CREATOR = new GsonBuilder().create();

    private RequestQueue mRequestQueue;

    public UVLiveGateway(Context context) {
        mRequestQueue = Volley.newRequestQueue(context);
    }

    public void status(Response.Listener<StatusResponse> responseListener, Response.ErrorListener errorListener) {
        GsonRequest<StatusResponse> peticion = new GsonRequest<>(environment+sStatus,
                StatusResponse.class,"{}",responseListener,errorListener);
        mRequestQueue.add(peticion);
    }

    public void login(LoginForm form, Response.Listener<LoginResponse> responseListener, Response.ErrorListener errorListener) {
        String stringRequest = GSON_CREATOR.toJson(form);
        GsonRequest<LoginResponse> peticion = new GsonRequest<>(environment+sLoginUrl,
                LoginResponse.class,stringRequest,responseListener,errorListener);
        mRequestQueue.add(peticion);
    }

    public void conversations(Response.Listener<ConversationsListResponse>
            responseListener, Response.ErrorListener errorListener) {
        GsonRequest<ConversationsListResponse> peticion = new GsonRequest<>(environment+sConversationsUrl,
                ConversationsListResponse.class,"",responseListener,errorListener);
        addRequestToQueue(peticion);
    }

    public void messages(MessagesForm form, Response.Listener<MessageListResponse> responseLinster, Response.ErrorListener errorListener) {
        MessageListResponse listResponse = new MessageListResponse();
        ArrayList<MessageResponse> list = new ArrayList<>();
        for (int i=0 ; i<1000 ; i++) {
            MessageResponse response = new MessageResponse();
            response.setMessage("Message "+i);
            response.setTimestamp(i);
            list.add(response);
        }
        listResponse.setMessageResponse(list);
        responseLinster.onResponse(listResponse);
    }

    private <T> void addRequestToQueue(GsonRequest<T> request) {
        Log.d(TAG,"GATEWAY:"+request.toString());
        mRequestQueue.add(request);
    }

    public void logs(Response.Listener<LogListResponse> responseListener, Response.ErrorListener errorListener) {
        addRequestToQueue(new GsonRequest<>(environment+sLoggerUrl,
                LogListResponse.class,"",responseListener,errorListener));
    }
}
