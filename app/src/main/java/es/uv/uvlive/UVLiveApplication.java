package es.uv.uvlive;

import android.app.Application;
import android.os.SystemClock;

import es.uv.uvlive.data.gateway.UVLiveGateway;

import java.util.concurrent.TimeUnit;

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
        // Don't do this! This is just so cold launches take some time
        SystemClock.sleep(TimeUnit.SECONDS.toMillis(3));
    }

    public static UVLiveGateway getUVLiveGateway(){
        return mUvLiveGateway;
    }

    public static UVLiveApplication getInstance(){
        return mInstance;
    }
}
