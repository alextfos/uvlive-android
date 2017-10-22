package es.uv.uvlive.data.gateway;

import android.content.Context;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.atraverf.uvlive.BuildConfig;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import es.uv.uvlive.data.UVCallback;
import es.uv.uvlive.data.gateway.form.BroadcastForm;
import es.uv.uvlive.data.gateway.form.InitConversationForm;
import es.uv.uvlive.data.gateway.form.LoginForm;
import es.uv.uvlive.data.gateway.form.MerchantRegisterForm;
import es.uv.uvlive.data.gateway.form.MessageForm;
import es.uv.uvlive.data.gateway.form.MessagesForm;
import es.uv.uvlive.data.gateway.form.PushTokenForm;
import es.uv.uvlive.data.gateway.form.StudentForm;
import es.uv.uvlive.data.gateway.response.BaseResponse;
import es.uv.uvlive.data.gateway.response.ConversationsListResponse;
import es.uv.uvlive.data.gateway.response.LogListResponse;
import es.uv.uvlive.data.gateway.response.LoginResponse;
import es.uv.uvlive.data.gateway.response.MerchantResponse;
import es.uv.uvlive.data.gateway.response.MessageListResponse;
import es.uv.uvlive.data.gateway.response.UserListResponse;
import es.uv.uvlive.data.gateway.response.ValidateMerchantResponse;
import es.uv.uvlive.session.Student;

public class UVLiveGateway {

    private static final String TAG = "UVLiveGateway";

    // User requests
    private static final String urlLogin = "user/login";
    private static final String urlLogout = "user/logout";

    // RolUV requests
    private static final String urlConversations = "rolUV/conversations";
    private static final String urlConversationsInit = "rolUV/conversations/init";
    private static final String urlUsers = "rolUV/users";
    private static final String urlMessages ="rolUV/messages";
    private static final String urlPreviousMessages = "rolUV/messages/previous";
    private static final String urlFollowingMessages = "rolUV/messages/following";
    private static final String urlSend = "rolUV/message/send";
    private static final String urlUpdatePushToken = "rolUV/pushToken/update";

    // Teacher requests
    private static final String urlBlockUser = "teacher/student/block";
    private static final String urlUnblockUser = "/teacher/student/unblock";

    // Admin requests
    private static final String urlLogger ="admin/logs";
    private static final String urlRegisterMerchant = "admin/merchant/register";
    private static final String urlValidateMerchant = "admin/merchantName/exists";
    private static final String urlUpdateMerchant = "admin/merchant/update";
    private static final String urlGetMerchant = "admin/merchant/get";

    // Merchant
    private static final String urlBradcastRegister = "merchant/broadcast/register";
    
    private static String environment = BuildConfig.ENVIRONMENT + "uvlive-api/";

    public static final Gson GSON_CREATOR = new GsonBuilder().create();

    private RequestQueue requestQueue;

    public UVLiveGateway(Context context) {
        requestQueue = Volley.newRequestQueue(context);
    }

    /*
    * User requests
    * */
    public void login(LoginForm form, UVCallback<LoginResponse> callback) {
        String stringRequest = GSON_CREATOR.toJson(form);
        GsonRequest<LoginResponse> request = new GsonRequest<>(environment + urlLogin,
                LoginResponse.class,stringRequest,callback.Listener,callback.ErrorListener);
        addRequestToQueue(request);
    }

    public void logout(UVCallback<BaseResponse> callback) {
        GsonRequest<BaseResponse> request = new GsonRequest<>(environment + urlLogout,
                BaseResponse.class,GSON_CREATOR.toJson(new Object()),callback.Listener,callback.ErrorListener);
        addRequestToQueue(request);
    }

    /*
    * RolUV requests
    * */
    public void updatePushToken(PushTokenForm pushTokenForm, UVCallback<BaseResponse> callback) {
        GsonRequest<BaseResponse> request = new GsonRequest<>(environment + urlUpdatePushToken,
                BaseResponse.class,GSON_CREATOR.toJson(pushTokenForm),callback.Listener,callback.ErrorListener);
        addRequestToQueue(request);
    }

    public void conversations(UVCallback<ConversationsListResponse> callback) {
        GsonRequest<ConversationsListResponse> request = new GsonRequest<>(environment + urlConversations,
                ConversationsListResponse.class,"",callback.Listener,callback.ErrorListener);
        addRequestToQueue(request);
    }

    public void getMessages(MessagesForm form, UVCallback<MessageListResponse> callback) {
        GsonRequest<MessageListResponse> request = new GsonRequest<>(environment + urlMessages,
                MessageListResponse.class,GSON_CREATOR.toJson(form), callback.Listener,callback.ErrorListener);
        addRequestToQueue(request);
    }

    public void getPreviousMessages(MessagesForm form, UVCallback<MessageListResponse> callback) {
        GsonRequest<MessageListResponse> request = new GsonRequest<>(environment + urlPreviousMessages,
                MessageListResponse.class,GSON_CREATOR.toJson(form), callback.Listener,callback.ErrorListener);
        addRequestToQueue(request);
    }

    public void getFollowingMessages(MessagesForm form, UVCallback<MessageListResponse> callback) {
        GsonRequest<MessageListResponse> request = new GsonRequest<>(environment + urlFollowingMessages,
                MessageListResponse.class,GSON_CREATOR.toJson(form), callback.Listener,callback.ErrorListener);
        addRequestToQueue(request);
    }

    public void getUsers(UVCallback<UserListResponse> callback) {
        GsonRequest<UserListResponse> request = new GsonRequest<>(environment + urlUsers,
                UserListResponse.class,"", callback.Listener,callback.ErrorListener);
        addRequestToQueue(request);
    }

    public void initConversation(InitConversationForm initConversationForm, UVCallback<BaseResponse> callback) {
        GsonRequest<BaseResponse> request = new GsonRequest<>(environment + urlConversationsInit,
                BaseResponse.class,GSON_CREATOR.toJson(initConversationForm), callback.Listener,callback.ErrorListener);
        addRequestToQueue(request);
    }

    public void sendMessage(MessageForm messageForm, UVCallback<BaseResponse> callback) {
        GsonRequest<BaseResponse> request = new GsonRequest<>(environment + urlSend,
                BaseResponse.class,GSON_CREATOR.toJson(messageForm),callback.Listener,callback.ErrorListener);
        addRequestToQueue(request);
    }

    /*
    * Teacher requests
    * */

    public void blockStudent(StudentForm studentForm, UVCallback<BaseResponse> callback) {
        GsonRequest<BaseResponse> request = new GsonRequest<>(environment + urlBlockUser,
                BaseResponse.class,GSON_CREATOR.toJson(studentForm),callback.Listener,callback.ErrorListener);
        addRequestToQueue(request);
    }

    public void unblockStudent(StudentForm studentForm, UVCallback<BaseResponse> callback) {
        GsonRequest<BaseResponse> request = new GsonRequest<>(environment + urlUnblockUser,
                BaseResponse.class,GSON_CREATOR.toJson(studentForm),callback.Listener,callback.ErrorListener);
        addRequestToQueue(request);
    }

    /*
    * Admin requests
    * */

    public void logs(UVCallback<LogListResponse> callback) {
        addRequestToQueue(new GsonRequest<>(environment + urlLogger,
                LogListResponse.class,"",callback.Listener,callback.ErrorListener));
    }

    public void merchantRegister(MerchantRegisterForm form, UVCallback<BaseResponse> callback) {
        String stringRequest = GSON_CREATOR.toJson(form);
        GsonRequest<BaseResponse> request = new GsonRequest<>(environment + urlRegisterMerchant,
                BaseResponse.class,stringRequest,callback.Listener,callback.ErrorListener);
        addRequestToQueue(request);
    }

    public void merchantUpdate(MerchantRegisterForm form, UVCallback<BaseResponse> callback) {
        String stringRequest = GSON_CREATOR.toJson(form);
        GsonRequest<BaseResponse> request = new GsonRequest<>(environment + urlUpdateMerchant,
                BaseResponse.class,stringRequest,callback.Listener,callback.ErrorListener);
        addRequestToQueue(request);
    }

    public void merchantValidate(MerchantRegisterForm form, UVCallback<ValidateMerchantResponse> callback) {
        String stringRequest = GSON_CREATOR.toJson(form);
        GsonRequest<ValidateMerchantResponse> request = new GsonRequest<>(environment + urlValidateMerchant,
                ValidateMerchantResponse.class,stringRequest,callback.Listener,callback.ErrorListener);
        addRequestToQueue(request);
    }

    /*
    * Merchant requests
    * */
    public void registerBroadcast(BroadcastForm form, UVCallback<BaseResponse> callback) {
        String stringRequest = GSON_CREATOR.toJson(form);
        GsonRequest<BaseResponse> request = new GsonRequest<>(environment + urlBradcastRegister,
                BaseResponse.class,stringRequest,callback.Listener,callback.ErrorListener);
        addRequestToQueue(request);
    }

    /*
    * Get Merchant requests
    * */
    public void getMerchant(MerchantRegisterForm form, UVCallback<MerchantResponse> callback) {
        String stringRequest = GSON_CREATOR.toJson(form);
        GsonRequest<MerchantResponse> request = new GsonRequest<>(environment + urlGetMerchant,
                MerchantResponse.class,stringRequest,callback.Listener,callback.ErrorListener);
        addRequestToQueue(request);
    }

    /*
    * Private methods
    * */
    private final <T> void addRequestToQueue(GsonRequest<T> request) {
        Log.d(TAG,"GATEWAY:" + request.toString());
        requestQueue.add(request);
    }
}
