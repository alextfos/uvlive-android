package es.uv.uvlive.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.raizlabs.android.dbflow.config.FlowManager;

import butterknife.ButterKnife;
import es.uv.uvlive.UVLiveApplication;
import es.uv.uvlive.data.ErrorManager;
import es.uv.uvlive.data.UVCallback;
import es.uv.uvlive.data.UVLivePreferences;
import es.uv.uvlive.data.database.UVLiveDB;
import es.uv.uvlive.data.gateway.GsonRequest;
import es.uv.uvlive.data.gateway.response.BaseResponse;
import es.uv.uvlive.ui.actions.BaseActions;


public abstract class BaseActivity extends AppCompatActivity implements BaseActions {

    protected int getLayoutId() {
        return 0;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getLayoutId() != 0) {
            setContentView(getLayoutId());
            ButterKnife.bind(this);
        }
        initializePresenter();
    }

    protected abstract void initializePresenter();

    @Override
    public void onError(int errorCode) {
        if (errorCode == ErrorManager.ERROR_CODE_TOKEN_EXPIRED) {
            logout();
        }
        Toast.makeText(this,getText(ErrorManager.getMessage(errorCode)),Toast.LENGTH_LONG).show();
    }

    public void logout() {
        UVLiveApplication.getUVLiveGateway().logout(new UVCallback<BaseResponse>() {
            @Override
            public void onSuccess(@NonNull BaseResponse baseResponse) {
                removeLocalData();
            }

            @Override
            public void onError(int errorCode) {
                removeLocalData();
            }
        });
        startLoginActivity();
        finish();
    }

    public void removeLocalData() {
        // TODO remove all database
        UVLivePreferences.getInstance().removeUser();
        GsonRequest.removeToken();
//        FlowManager.getDatabase(UVLiveDB.DATABASE_NAME).reset(getApplicationContext());
    }

    protected void startLoginActivity() {
        Intent intent = new Intent(BaseActivity.this,LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        startActivity(intent);
    }
}
