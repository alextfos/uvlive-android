package es.uv.uvlive;

import android.app.Application;
import android.os.SystemClock;
import android.support.annotation.Nullable;

import com.crashlytics.android.Crashlytics;

import es.uv.uvlive.data.UVLivePreferences;
import com.raizlabs.android.dbflow.config.FlowConfig;
import com.raizlabs.android.dbflow.config.FlowManager;

import es.uv.uvlive.data.gateway.UVLiveGateway;
import io.fabric.sdk.android.Fabric;

import java.util.concurrent.TimeUnit;

/**
 * Created by atraverf on 17/11/15.
 */
public class UVLiveApplication extends Application {

    private static UVLiveApplication mInstance;
    private static UVLiveGateway mUvLiveGateway;

    @Override
    public void onCreate() {
        super.onCreate();

        Fabric.with(this, new Crashlytics());

        //SingletonPattern
        mInstance=this;
        mUvLiveGateway = new UVLiveGateway(this);
        FlowManager.init(new FlowConfig.Builder(this)
                .openDatabasesOnInit(true).build());

        // Don't do this! This is just so cold launches take some time
        SystemClock.sleep(TimeUnit.SECONDS.toMillis(3));
    }

    public static UVLiveGateway getUVLiveGateway() {
        return mUvLiveGateway;
    }

    public static UVLiveApplication getInstance() {
        return mInstance;
    }
}
