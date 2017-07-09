package es.uv.uvlive.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import es.uv.uvlive.UVLiveApplication;
import es.uv.uvlive.data.ErrorManager;
import es.uv.uvlive.data.UVCallback;
import es.uv.uvlive.data.UVLivePreferences;
import es.uv.uvlive.data.gateway.GsonRequest;
import es.uv.uvlive.data.gateway.response.BaseResponse;
import es.uv.uvlive.ui.actions.BaseActions;


public abstract class BaseActivity extends AppCompatActivity implements BaseActions {

    protected static int LOGIN_REQUEST_CODE = 100;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
    }

    protected void startLoginActivity() {
        startActivityForResult(new Intent(BaseActivity.this,LoginActivity.class),LOGIN_REQUEST_CODE);
    }
}
