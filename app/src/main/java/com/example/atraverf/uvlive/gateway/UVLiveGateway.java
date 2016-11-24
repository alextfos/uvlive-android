package com.example.atraverf.uvlive.gateway;

import android.content.Context;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.android.volley.RequestQueue;
import com.example.atraverf.uvlive.gateway.form.ConversationsForm;
import com.example.atraverf.uvlive.gateway.form.LoginForm;
import com.example.atraverf.uvlive.gateway.form.MessagesForm;
import com.example.atraverf.uvlive.gateway.response.ConversationsListResponse;
import com.example.atraverf.uvlive.gateway.response.LoginResponse;
import com.example.atraverf.uvlive.gateway.response.MessagesResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created by atraverf on 17/11/15.
 */
public class UVLiveGateway {

    private static final String CURRENT_ACCEPT_LANGUAGE_HEADER_VALUE = "es";
    private static String headerCookie;
    private static String sLoginUrl = "/login";
    private static String sConversationsUrl ="/conversations";

    private static String environment = "http://192.168.137.229:8080/uvlive-api-1.0-SNAPSHOT/";
    public static final Gson GSON_CREATOR = new GsonBuilder().create();

    private RequestQueue mRequestQueue;

    public UVLiveGateway(Context context){
        mRequestQueue = Volley.newRequestQueue(context);
    }

    public void login(LoginForm form ,Response.Listener<LoginResponse> responseListener ,Response.ErrorListener errorListener) {
        //add to list
        String stringRequest = GSON_CREATOR.toJson(form);
        GsonRequest<LoginResponse> peticion = new GsonRequest<LoginResponse>(environment+sLoginUrl,
                LoginResponse.class,stringRequest,responseListener,errorListener);
        Log.d("GATEWAY - REQUEST: ",peticion.toString());
        mRequestQueue.add(peticion);
    }

    public void conversations(ConversationsForm form ,Response.Listener<ConversationsListResponse> responseListener ,Response.ErrorListener errorListener) {
        //add to list
        String stringRequest = GSON_CREATOR.toJson(form);
        GsonRequest<ConversationsListResponse> peticion = new GsonRequest<ConversationsListResponse>(environment+sConversationsUrl,
                ConversationsListResponse.class,stringRequest,responseListener,errorListener);
        Log.d("GATEWAY - REQUEST: ",peticion.toString());
        mRequestQueue.add(peticion);
    }

    public void messages(MessagesForm form, Response.Listener<MessagesResponse> responseLinster, Response.ErrorListener errorListener) {

    }

    public static void setCookie(String cookie) {
        headerCookie = cookie;
    }

    public static String getCookie() {
        return headerCookie;
    }
    /*
    private <T> void addRequestToQueue(Request<T> request){
        if (request instanceof IDKGsonRequest) {
            ((IDKGsonRequest) request)
                    .setCurrentAcceptLanguageHeaderValue(CURRENT_ACCEPT_LANGUAGE_HEADER_VALUE);
        }
        mRequestQueue.add(request);
    }
    */
}
