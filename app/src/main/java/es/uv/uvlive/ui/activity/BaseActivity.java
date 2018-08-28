package es.uv.uvlive.ui.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.raizlabs.android.dbflow.sql.language.SQLite;

import butterknife.ButterKnife;
import es.uv.uvlive.UVLiveApplication;
import es.uv.uvlive.data.database.models.ConversationTable;
import es.uv.uvlive.data.database.models.MessageTable;
import es.uv.uvlive.mappers.ErrorMapper;
import es.uv.uvlive.data.UVCallback;
import es.uv.uvlive.data.UVLivePreferences;
import es.uv.uvlive.data.gateway.GsonRequest;
import es.uv.uvlive.data.gateway.response.BaseResponse;
import es.uv.uvlive.presenter.BasePresenter;
import es.uv.uvlive.session.BusinessError;
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
        if (getPresenter() != null) {
            getPresenter().onCreate();
        }
    }

    protected abstract void initializePresenter();

    @Override
    public void onError(BusinessError businessError) {
        if (businessError == BusinessError.SESSION_EXPIRED) {
            logout();
        }
        Toast.makeText(this,getText(ErrorMapper.getMessage(businessError)),Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (getPresenter() != null) {
            getPresenter().onDestroy();
        }
    }

    protected @Nullable BasePresenter getPresenter() {
        return null;
    }

    public void logout() {
        UVLivePreferences.getInstance().removeAll();
        SQLite.delete().from(MessageTable.class).execute();
        SQLite.delete().from(ConversationTable.class).execute();
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
