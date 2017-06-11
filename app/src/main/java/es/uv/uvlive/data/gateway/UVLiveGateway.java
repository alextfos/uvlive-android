package es.uv.uvlive.data.gateway;

import android.content.Context;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
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
    private static final String sLoginUrl = "/login";
    private static final String sConversationsUrl ="/conversations";
    private static final String sGetMessagesUrl ="/messages";
    private static final String sLoggerUrl ="/logger";
    private static final String sPushToken = "/update/push_token";
    private static final String sSend = "/send";

    // Integrated emulator
//    private static String environment = "http://10.0.2.2:8080/uvlive-api";
//    private static String environment = "http://192.168.1.10:8080/uvlive-api";
    private static String environment = "http://172.20.10.3:8080/uvlive-api";
//    private static String environment = "http://10.0.3.2:8080/uvlive-api";
    public static final Gson GSON_CREATOR = new GsonBuilder().create();

    private RequestQueue mRequestQueue;

    public UVLiveGateway(Context context) {
        mRequestQueue = Volley.newRequestQueue(context);
    }

    public void login(LoginForm form, UVCallback<LoginResponse> callback) {
        String stringRequest = GSON_CREATOR.toJson(form);
        GsonRequest<LoginResponse> request = new GsonRequest<>(environment+sLoginUrl,
                LoginResponse.class,stringRequest,callback.Listener,callback.ErrorListener);
        addRequestToQueue(request);
    }

    public void updatePushToken(PushTokenForm pushTokenForm, UVCallback<BaseResponse> callback) {
        GsonRequest<BaseResponse> request = new GsonRequest<>(environment+sPushToken,
                BaseResponse.class,GSON_CREATOR.toJson(pushTokenForm),callback.Listener,callback.ErrorListener);
        addRequestToQueue(request);
    }

    public void conversations(UVCallback<ConversationsListResponse> callback) {
        GsonRequest<ConversationsListResponse> request = new GsonRequest<>(environment+sConversationsUrl,
                ConversationsListResponse.class,"",callback.Listener,callback.ErrorListener);
        addRequestToQueue(request);
    }

    public void getMessages(MessagesForm form, UVCallback<MessageListResponse> callback) {
        GsonRequest<MessageListResponse> request = new GsonRequest<>(environment+sGetMessagesUrl,
                MessageListResponse.class,GSON_CREATOR.toJson(form), callback.Listener,callback.ErrorListener);
        addRequestToQueue(request);
    }

    public void sendMessage(MessageForm messageForm, UVCallback<BaseResponse> callback) {
        GsonRequest<BaseResponse> request = new GsonRequest<>(environment+sSend,
                BaseResponse.class,GSON_CREATOR.toJson(messageForm),callback.Listener,callback.ErrorListener);
        addRequestToQueue(request);
    }

    public void logs(UVCallback<LogListResponse> callback) {
        addRequestToQueue(new GsonRequest<>(environment+sLoggerUrl,
                LogListResponse.class,"",callback.Listener,callback.ErrorListener));
    }

    private final <T> void addRequestToQueue(GsonRequest<T> request) {
        Log.d(TAG,"GATEWAY:"+request.toString());
        mRequestQueue.add(request);
    }
}
