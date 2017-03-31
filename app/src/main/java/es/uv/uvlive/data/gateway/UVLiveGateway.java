package es.uv.uvlive.data.gateway;

import android.content.Context;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import es.uv.uvlive.data.gateway.form.LoginForm;
import es.uv.uvlive.data.gateway.form.MessagesForm;
import es.uv.uvlive.data.gateway.response.BaseResponse;
import es.uv.uvlive.data.gateway.response.ConversationsListResponse;
import es.uv.uvlive.data.gateway.response.LogListResponse;
import es.uv.uvlive.data.gateway.response.LoginResponse;
import es.uv.uvlive.data.gateway.response.MessageListResponse;

/**
 * Created by atraverf on 17/11/15.
 */
public class UVLiveGateway {

    private static final String TAG = "UVLiveGateway";
    private static final String sLoginUrl = "/login";
    private static final String sConversationsUrl ="/conversations";
    private static final String sGetMessagesUrl ="/messages";
    private static final String sLoggerUrl ="/logger";
    private static final String sStatus = "/status";

//    private static String environment = "http://10.0.2.2:8080/uvlive-api-1.0-SNAPSHOT";
    private static String environment = "http://slopez.uv.es:8080/uvlive-api-1.0-SNAPSHOT";
    public static final Gson GSON_CREATOR = new GsonBuilder().create();

    private RequestQueue mRequestQueue;

    public UVLiveGateway(Context context) {
        mRequestQueue = Volley.newRequestQueue(context);
    }

    public void status(Response.Listener<BaseResponse> responseListener, Response.ErrorListener errorListener) {
        GsonRequest<BaseResponse> peticion = new GsonRequest<>(environment+sStatus,
                BaseResponse.class,"",responseListener,errorListener);
        addRequestToQueue(peticion);
    }

    public void login(LoginForm form, Response.Listener<LoginResponse> responseListener, Response.ErrorListener errorListener) {
        String stringRequest = GSON_CREATOR.toJson(form);
        GsonRequest<LoginResponse> peticion = new GsonRequest<>(environment+sLoginUrl,
                LoginResponse.class,stringRequest,responseListener,errorListener);
        addRequestToQueue(peticion);
    }

    public void updatePushToken(String pushToken, Response.Listener<BaseResponse> responseListener, Response.ErrorListener errorListener) {
        GsonRequest<BaseResponse> peticion = new GsonRequest<>(environment+sStatus,
                BaseResponse.class,GSON_CREATOR.toJson(pushToken),responseListener,errorListener);
        addRequestToQueue(peticion);
    }

    public void conversations(Response.Listener<ConversationsListResponse>
            responseListener, Response.ErrorListener errorListener) {
        GsonRequest<ConversationsListResponse> peticion = new GsonRequest<>(environment+sConversationsUrl,
                ConversationsListResponse.class,"",responseListener,errorListener);
        addRequestToQueue(peticion);
    }

    public void getMessages(MessagesForm form, Response.Listener<MessageListResponse> responseListener, Response.ErrorListener errorListener) {
        GsonRequest<MessageListResponse> peticion = new GsonRequest<>(environment+sGetMessagesUrl,
                MessageListResponse.class,GSON_CREATOR.toJson(form),responseListener,errorListener);
        addRequestToQueue(peticion);
    }

    public void logs(Response.Listener<LogListResponse> responseListener, Response.ErrorListener errorListener) {
        addRequestToQueue(new GsonRequest<>(environment+sLoggerUrl,
                LogListResponse.class,"",responseListener,errorListener));
    }

    private final <T> void addRequestToQueue(GsonRequest<T> request) {
        Log.d(TAG,"GATEWAY:"+request.toString());
        mRequestQueue.add(request);
    }
}
