package es.uv.uvlive;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.os.SystemClock;
import android.support.annotation.Nullable;

import com.crashlytics.android.Crashlytics;
import com.raizlabs.android.dbflow.config.FlowConfig;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.squareup.leakcanary.LeakCanary;

import java.util.List;
import java.util.concurrent.TimeUnit;

import es.uv.uvlive.data.gateway.UVLiveGateway;
import es.uv.uvlive.session.Session;
import es.uv.uvlive.ui.actions.BaseActions;
import io.fabric.sdk.android.Fabric;

/**
 * Created by atraverf on 17/11/15.
 */
public class UVLiveApplication extends Application {

    private static UVLiveApplication mInstance;
    private static UVLiveGateway mUvLiveGateway;

    private static BaseActions sBaseActions;

    @Override
    public void onCreate() {
        super.onCreate();
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
        Fabric.with(this, new Crashlytics());

        //SingletonPattern
        mInstance=this;
        mUvLiveGateway = new UVLiveGateway(this);
        FlowManager.init(new FlowConfig.Builder(this)
                .openDatabasesOnInit(true).build());

        // Initialize App
        Session.getInstance().loadUser();
    }

    public boolean isApplicationInForeground() {
        boolean isActivityFound = false;
        ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);

        if (activityManager != null) {
            List<ActivityManager.RunningAppProcessInfo> services = activityManager.getRunningAppProcesses();

            if (services.get(0).processName
                    .equalsIgnoreCase(getPackageName())) {
                isActivityFound = true;
            }
        }

        return isActivityFound;
    }

    public static void subscribeActions(BaseActions baseActions) {
        sBaseActions = baseActions;
    }

    public static void unsubscribeActions(BaseActions baseActions) {
        if (sBaseActions != null && sBaseActions.equals(baseActions)) {
            sBaseActions = null;
        }
    }

    public static @Nullable BaseActions getBaseActions() {
        return sBaseActions;
    }

    public static UVLiveGateway getUVLiveGateway() {
        return mUvLiveGateway;
    }

    public static UVLiveApplication getInstance() {
        return mInstance;
    }
}
