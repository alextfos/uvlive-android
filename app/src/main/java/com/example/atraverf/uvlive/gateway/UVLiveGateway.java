package com.example.atraverf.uvlive.gateway;

import android.content.Context;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.toolbox.Volley;
import com.android.volley.RequestQueue;
import com.example.atraverf.uvlive.gateway.form.LoginForm;
import com.example.atraverf.uvlive.gateway.model.LoginModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created by atraverf on 17/11/15.
 */
public class UVLiveGateway {

    private static final String CURRENT_ACCEPT_LANGUAGE_HEADER_VALUE = "es";
    private static String sLoginUrl = "ruta";

    private static String environment = "http://localhost:8080/UVLive/";
    public static final Gson GSON_CREATOR = new GsonBuilder().create();

    public static final RetryPolicy IDK_RETRY_POLICY = new DefaultRetryPolicy(60000,
            0, 0f);

    private RequestQueue mRequestQueue;

    public UVLiveGateway(Context context){
        RequestQueue mRequestQueue = Volley.newRequestQueue(context);
    }

    public void login(LoginForm form ,Response.Listener<LoginModel> responseListener ,Response.ErrorListener errorListener) {
        //añadir a la lista
        String stringRequest = GSON_CREATOR.toJson(responseListener);

        IDKGsonRequest<LoginModel> request = new IDKGsonRequest<LoginModel>(Request.Method.POST,
                LoginModel.class,sLoginUrl,stringRequest,responseListener,errorListener);

    }

    private <T> void addRequestToQueue(Request<T> request){
        if (request instanceof IDKGsonRequest) {
            ((IDKGsonRequest) request)
                    .setCurrentAcceptLanguageHeaderValue(CURRENT_ACCEPT_LANGUAGE_HEADER_VALUE);
        }
        mRequestQueue.add(request);
    }
}
