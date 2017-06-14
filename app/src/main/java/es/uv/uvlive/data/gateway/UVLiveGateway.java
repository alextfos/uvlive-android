package es.uv.uvlive.data.gateway;

import android.content.Context;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.atraverf.uvlive.BuildConfig;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import es.uv.uvlive.data.UVCallback;
import es.uv.uvlive.data.gateway.form.LoginForm;
import es.uv.uvlive.data.gateway.form.MessageForm;
import es.uv.uvlive.data.gateway.form.MessagesForm;
import es.uv.uvlive.data.gateway.form.PushTokenForm;
import es.uv.uvlive.data.gateway.response.BaseResponse;
import es.uv.uvlive.data.gateway.response.ConversationsListResponse;
import es.uv.uvlive.data.gateway.response.LogListResponse;
import es.uv.uvlive.data.gateway.response.LoginResponse;
import es.uv.uvlive.data.gateway.response.MessageListResponse;

public class UVLiveGateway {

    private static final String TAG = "UVLiveGateway";
    private static final String urlLogin = "login";
    private static final String urlLogout = "logout";
    private static final String urlConversations ="conversations";
    private static final String urlMessages ="messages";
    private static final String urlLogger ="logger";
    private static final String urlPushToken = "update/push_token";
    private static final String urlSend = "urlSend";

    private static String environment = BuildConfig.ENVIRONMENT + "uvlive-api/";

    public static final Gson GSON_CREATOR = new GsonBuilder().create();

    private RequestQueue requestQueue;

    public UVLiveGateway(Context context) {
        requestQueue = Volley.newRequestQueue(context);
    }

    public void login(LoginForm form, UVCallback<LoginResponse> callback) {
        String stringRequest = GSON_CREATOR.toJson(form);
        GsonRequest<LoginResponse> request = new GsonRequest<>(environment+ urlLogin,
                LoginResponse.class,stringRequest,callback.Listener,callback.ErrorListener);
        addRequestToQueue(request);
    }

    public void logout(UVCallback<BaseResponse> callback) {
        GsonRequest<BaseResponse> request = new GsonRequest<>(environment+ urlLogout,
                BaseResponse.class,"",callback.Listener,callback.ErrorListener);
        addRequestToQueue(request);
    }

    public void updatePushToken(PushTokenForm pushTokenForm, UVCallback<BaseResponse> callback) {
        GsonRequest<BaseResponse> request = new GsonRequest<>(environment+ urlPushToken,
                BaseResponse.class,GSON_CREATOR.toJson(pushTokenForm),callback.Listener,callback.ErrorListener);
        addRequestToQueue(request);
    }

    public void conversations(UVCallback<ConversationsListResponse> callback) {
        GsonRequest<ConversationsListResponse> request = new GsonRequest<>(environment+ urlConversations,
                ConversationsListResponse.class,"",callback.Listener,callback.ErrorListener);
        addRequestToQueue(request);
    }

    public void getMessages(MessagesForm form, UVCallback<MessageListResponse> callback) {
        GsonRequest<MessageListResponse> request = new GsonRequest<>(environment+ urlMessages,
                MessageListResponse.class,GSON_CREATOR.toJson(form), callback.Listener,callback.ErrorListener);
        addRequestToQueue(request);
    }

    public void sendMessage(MessageForm messageForm, UVCallback<BaseResponse> callback) {
        GsonRequest<BaseResponse> request = new GsonRequest<>(environment+ urlSend,
                BaseResponse.class,GSON_CREATOR.toJson(messageForm),callback.Listener,callback.ErrorListener);
        addRequestToQueue(request);
    }

    public void logs(UVCallback<LogListResponse> callback) {
        addRequestToQueue(new GsonRequest<>(environment+ urlLogger,
                LogListResponse.class,"",callback.Listener,callback.ErrorListener));
    }

    private final <T> void addRequestToQueue(GsonRequest<T> request) {
        Log.d(TAG,"GATEWAY:"+request.toString());
        requestQueue.add(request);
    }
}
