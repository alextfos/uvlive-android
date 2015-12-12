package com.example.atraverf.uvlive;

import android.app.Activity;
import android.app.Application;

import com.example.atraverf.uvlive.gateway.UVLiveGateway;

/**
 * Created by atraverf on 17/11/15.
 */
public class UVLiveApplication extends Application {

    private static UVLiveApplication mInstance;
    private static UVLiveGateway mUvLiveGateway;
    private static final String UV_LIVE_VERSION = "UVLive Develop";

    @Override
    public void onCreate(){
        super.onCreate();

        //SingletonPattern
        mInstance=this;
        mUvLiveGateway = new UVLiveGateway(this);
    }

    public static UVLiveGateway getUVLiveGateway(){
        return mUvLiveGateway;
    }

    public static UVLiveApplication getInstance(){
        return mInstance;
    }
}
